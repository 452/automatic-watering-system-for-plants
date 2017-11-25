package com.github.eclipse.kura.components;

import static org.eclipse.kura.camel.runner.CamelRunner.createOsgiRegistry;
import static org.osgi.framework.FrameworkUtil.getBundle;
import static org.osgi.service.component.annotations.ConfigurationPolicy.REQUIRE;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.eclipse.kura.camel.bean.PayloadFactory;
import org.eclipse.kura.camel.runner.CamelRunner;
import org.eclipse.kura.configuration.ConfigurableComponent;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.metatype.annotations.Designate;

import com.github.eclipse.kura.routes.WateringSystemRouteBuilder;

@Designate(ocd = com.github.eclipse.kura.configs.Config.class)
@Component(immediate = true, configurationPolicy = REQUIRE)
public class WateringSystemComponent implements ConfigurableComponent {

    private static final String APACHE_CAMEL_SERVLET_NAME = "wateringSystem";
    private static final String REST_URL_AUTOMATIC_WATERING_SYSTEM = "/aws";
    private CamelRunner runner;
    private ServiceReference httpServiceRef;
    private boolean registerService;
    private BundleContext bundleContext = null;

    @Activate
    public void activate(Map<String, Object> properties) throws Exception {
        this.runner = createRunner(properties);
        if (this.runner != null) {
            this.runner.setRoutes(new WateringSystemRouteBuilder());
            this.runner.start();
        }
    }

    @Modified
    public void modified(Map<String, Object> properties) throws Exception {
        deactivate();
        activate(properties);
    }

    @Deactivate
    public void deactivate() {
        if (this.runner != null) {
            this.runner.stop();
            this.runner = null;
        }
        unRegisterServlet();
    }

    private CamelRunner createRunner(final Map<String, ?> properties) throws Exception {
        final Map<String, Object> services = new HashMap<>();
        services.put("payloadFactory", new PayloadFactory());

        bundleContext = getBundle(WateringSystemComponent.class).getBundleContext();
        registerServlet(bundleContext);
        final CamelRunner.Builder builder = new CamelRunner.Builder(bundleContext).disableJmx(false)
                .requireComponent("stream").requireComponent("timer")
                .registryFactory(createOsgiRegistry(bundleContext, services));
        return builder.build();
    }

    private void registerServlet(BundleContext bundleContext) throws Exception {
        httpServiceRef = bundleContext.getServiceReference(HttpService.class.getName());
        if (httpServiceRef != null && !registerService) {
            final HttpService httpService = (HttpService) bundleContext.getService(httpServiceRef);
            if (httpService != null) {
                final HttpContext httpContext = httpService.createDefaultHttpContext();
                final Dictionary<String, String> initParams = new Hashtable<String, String>();
                initParams.put("matchOnUriPrefix", "false");
                initParams.put("servlet-name", APACHE_CAMEL_SERVLET_NAME);
                httpService.registerServlet(REST_URL_AUTOMATIC_WATERING_SYSTEM, new CamelHttpTransportServlet(),
                        initParams, httpContext);
                registerService = true;
            }
        }
    }

    private void unRegisterServlet() {
        if (httpServiceRef != null) {
            final HttpService httpService = (HttpService) bundleContext.getService(httpServiceRef);
            if (httpService != null) {
                httpService.unregister(REST_URL_AUTOMATIC_WATERING_SYSTEM);
            }
            bundleContext.ungetService(httpServiceRef);
            httpServiceRef = null;
        }
    }
}