/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.
 *
 * All Rights Reserved.
 ***********************************************************************/
package com.lloydstsb.rest.v1.logging;

import com.lloydstsb.rest.v1.constants.HeaderConstants;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/logging")
public interface SubmitCrashReportService {

    @POST
    @Path("client/crash")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Descriptions({
            @Description(target = DocTarget.METHOD, value = "Ingests and logs application crash reports."),
            @Description(target = DocTarget.REQUEST, value = "Standard http request"),
            @Description(target = DocTarget.RESPONSE, value = "Response is success or failure of logging action.")
    })
    public void submitApplicationCrashReports(@Description(value = "Most recent report of client application crashes.", target = DocTarget.PARAM)
                                      @FormParam("report")
                                      @NotNull(message = "Received report can not be null.")
                                      List<String> applicationCrashReports);
}
