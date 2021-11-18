package no.nnsn.quakemlwebservice.controller;

import org.glassfish.jersey.server.monitoring.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Map;

@Path("statistics")
public class JerseyStatisticsController {
    @Inject
    Provider<MonitoringStatistics> monitoringStatisticsProvider;

    @GET
    public String getTotal() {
        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get().snapshot();

        final TimeWindowStatistics timeWindowStatistics
                = snapshot.getRequestStatistics()
                .getTimeWindowStatistics().get(0l);

        ExceptionMapperStatistics exceptionMapperStatistics = snapshot.getExceptionMapperStatistics();
        long totalMappings = exceptionMapperStatistics.getTotalMappings();

        long timeWindow = timeWindowStatistics.getTimeWindow();

        return "request count: " + timeWindowStatistics.getRequestCount()
                + ", average request processing [ms]: "
                + timeWindowStatistics.getAverageDuration() + " total mappings: " + totalMappings
                + " timewindow: " + timeWindow;
    }

    @Path("/time/{time}")
    @GET
    public String getRequestByTime(@PathParam("time") Long time) {
        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get().snapshot();

        final TimeWindowStatistics timeWindowStatistics
                = snapshot.getRequestStatistics()
                .getTimeWindowStatistics().get(time);

        return "request count: " + timeWindowStatistics.getRequestCount()
                + ", average request processing [ms]: " + timeWindowStatistics.getAverageDuration();
    }

    @Path("/uri/builder")
    @GET
    public String getRequestByQueryBuilder() {
        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get().snapshot();

        ResourceStatistics resourceStatistics = snapshot.getUriStatistics().get("/query");
        ExecutionStatistics requestExecutionStatistics = resourceStatistics.getRequestExecutionStatistics();
        Map<Long, TimeWindowStatistics> timeWindowStatistics1 = requestExecutionStatistics.getTimeWindowStatistics();
        TimeWindowStatistics timeWindowStatistics = timeWindowStatistics1.get(0l);

        return "Total request count for QueryBuilder page: " + timeWindowStatistics.getRequestCount()
                + ", average request processing [ms]: " + timeWindowStatistics.getAverageDuration();
    }

    @Path("/uri/query")
    @GET
    public String getRequestByQuery() {
        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get().snapshot();

        ResourceStatistics resourceStatistics = snapshot.getUriStatistics().get("/query");
        ExecutionStatistics requestExecutionStatistics = resourceStatistics.getRequestExecutionStatistics();
        Map<Long, TimeWindowStatistics> timeWindowStatistics1 = requestExecutionStatistics.getTimeWindowStatistics();
        TimeWindowStatistics timeWindowStatistics = timeWindowStatistics1.get(0l);

        return "Total request count for Query: " + timeWindowStatistics.getRequestCount()
                + ", average request processing [ms]: " + timeWindowStatistics.getAverageDuration();
    }
}
