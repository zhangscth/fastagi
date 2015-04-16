package com.trumpia.properties;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.live.AsteriskChannel;

/**
 * Created by Logan K. on 13. 12. 27.오전 9:55
 */

// http://www.voip-info.org/wiki/view/Asterisk+Detailed+Variable+List
public class AsteriskApplication {

    public static class Dial {

        public static final String           Application = "Dial";
        public static final DetailedVariable DIALSTATUS  = new DetailedVariable("DIALSTATUS");
    }

    public static class Sip {

        public static final DetailedVariable SIPCALLID = new DetailedVariable("SIPCALLID");
    }

    public static class DetailedVariable {

        private final String expression;

        public DetailedVariable(final String expression) {

            this.expression = expression;
        }

        public String fullVariable(final AgiChannel agiChannel) throws AgiException {

            return agiChannel.getFullVariable("${" + expression + "}");
        }

        public String variable(final AgiChannel agiChannel) throws AgiException {

            return agiChannel.getVariable(expression);
        }

        public String fullVariable(final AsteriskChannel asteriskChannel) {

            return asteriskChannel.getVariable(expression);
        }

        public String getExpression() {

            return expression;
        }
    }
}
