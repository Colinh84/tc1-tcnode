package com.topcoder.web.tc.controller.request.tournament.tco10;

import com.topcoder.shared.util.EmailEngine;
import com.topcoder.shared.util.TCSEmailMessage;
import com.topcoder.web.common.NavigationException;
import com.topcoder.web.common.StringUtils;
import com.topcoder.web.tc.Constants;
import com.topcoder.web.tc.controller.request.Base;

/**
 * @author pulky, ivern
 * @version $Revision: 68126 $Date:
 */
public class SubmitRsvp extends Base {

    protected void businessProcessing() throws Exception {
        if ("POST".equals(getRequest().getMethod())) {
            String attending = StringUtils.checkNull(getRequest().getParameter("attending"));
            String name = StringUtils.checkNull(getRequest().getParameter("name"));
            String email = StringUtils.checkNull(getRequest().getParameter("email"));
            String company = StringUtils.checkNull(getRequest().getParameter("company"));
            String guests = StringUtils.checkNull(getRequest().getParameter("guests"));

            sendEmail(attending, name, email, company, guests);

            getRequest().setAttribute("attending", new Boolean(!attending.trim().equalsIgnoreCase("not")));
            setNextPage("/tournaments/tco10/rsvpSuccess.jsp");
            setIsNextPageInContext(true);
        } else {
            throw new NavigationException("Invalid request type.");
        }
    }

    private void sendEmail(String attending, String name, String email, String company, String guests) throws Exception {

        TCSEmailMessage mail = new TCSEmailMessage();
        mail.setSubject("TCO10 RSVP");

        StringBuffer msgText = new StringBuffer(3000);

        msgText.append("I will ");
        msgText.append(attending);
        msgText.append("be attending:\n");
        msgText.append("Name: ");
        msgText.append(name + "\n");
        msgText.append("Email: ");
        msgText.append(email + "\n");
        msgText.append("Company: ");
        msgText.append(company + "\n");
        msgText.append("Guests: ");
        msgText.append(guests + "\n");

        msgText.append("\n\n");

        mail.setBody(msgText.toString());
        mail.addToAddress(Constants.RSVP_TO_ADDRESS, TCSEmailMessage.TO);

        mail.setFromAddress("service@topcoder.com");
        EmailEngine.send(mail);
    }
}
