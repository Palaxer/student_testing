package org.palax.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code InvalidData} class is a data transfer object that is used to inform the view layer
 * about the incorrect input data from the user and determine what kind of class that is used for input field
 *
 * @author Taras Palashynskyy
 */
public class InvalidData {
    private String invalidLogin;
    private String invalidName;
    private String invalidSurname;
    private String invalidPasswd;
    private String invalidConfirmPasswd;
    private String invalidDesc;
    private String invalidPassScore;
    private String invalidPassTime;
    private String invalidText;
    private List<Long> invalidAnswersId;

    private InvalidData() {

    }

    public String getInvalidLogin() {
        return invalidLogin;
    }

    public String getInvalidName() {
        return invalidName;
    }

    public String getInvalidSurname() {
        return invalidSurname;
    }

    public String getInvalidPasswd() {
        return invalidPasswd;
    }

    public String getInvalidConfirmPasswd() {
        return invalidConfirmPasswd;
    }

    public String getInvalidDesc() {
        return invalidDesc;
    }

    public String getInvalidPassScore() {
        return invalidPassScore;
    }

    public String getInvalidPassTime() {
        return invalidPassTime;
    }

    public String getInvalidText() {
        return invalidText;
    }

    public List<Long> getInvalidAnswersId() {
        return invalidAnswersId;
    }

    /**
     * Returns {@link Builder} for building {@link InvalidData} object
     *
     * @param attribute {@code attribute} specifies what a class to use in view layer
     * @return {@link Builder} for building {@link InvalidData} object
     */
    public static Builder newBuilder(String attribute) {
        return new InvalidData().new Builder(attribute);
    }

    /**
     * The {@code Builder} is a inner class which building {@link InvalidData} instance
     *
     * @author Taras Palashynskyy
     */
    public class Builder {
        /** The value is used to specifies what a class to use in view layer */
        private final String attribute;

        /**
         * Constructor to create {@link Builder} instance
         *
         * @param attribute {@code attribute} specifies what a class to use in view layer
         */
        private Builder(String attribute) {
            this.attribute = attribute;
        }

        public Builder setInvalidLoginAttr() {
            InvalidData.this.invalidLogin = attribute;

            return this;
        }

        public Builder setInvalidNameAttr() {
            InvalidData.this.invalidName = attribute;

            return this;
        }

        public Builder setInvalidSurnameAttr() {
            InvalidData.this.invalidSurname = attribute;

            return this;
        }

        public Builder setInvalidPasswdAttr() {
            InvalidData.this.invalidPasswd = attribute;

            return this;
        }

        public Builder setInvalidConfirmPasswdAttr() {
            InvalidData.this.invalidConfirmPasswd = attribute;

            return this;
        }

        public Builder setInvalidDescAttr() {
            InvalidData.this.invalidDesc = attribute;

            return this;
        }

        public Builder setInvalidPassScoreAttr() {
            InvalidData.this.invalidPassScore = attribute;

            return this;
        }

        public Builder setInvalidPassTimeAttr() {
            InvalidData.this.invalidPassTime = attribute;

            return this;
        }

        public Builder setInvalidTextAttr() {
            InvalidData.this.invalidText = attribute;

            return this;
        }

        public Builder setInvalidAnswerId(Long id) {
            if(InvalidData.this.invalidAnswersId == null) {
                InvalidData.this.invalidAnswersId = new ArrayList<>();
            }

            InvalidData.this.invalidAnswersId.add(id);

            return this;
        }

        /**
         * Method used to get initialized instance of {@link InvalidData}
         *
         * @return initialized instance of {@link InvalidData}
         */
        public InvalidData build() {
            return InvalidData.this;
        }
    }
}
