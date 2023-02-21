package ru.clevertec.task.utils;

import ru.clevertec.task.exceptions.FileWritingException;

public enum AlignFormatter {
    CENTER {
        @Override
        protected String alignString(String value, int resultLength) {
            int valueLength = value.length();
            int leftSide = (resultLength - valueLength) / 2;
            int rightSide = resultLength - leftSide - valueLength;
            String whiteSpace = " ";
            String leftSideSpace = whiteSpace.repeat(leftSide);
            String rightSideSpace = whiteSpace.repeat(rightSide);
            return leftSideSpace + value + rightSideSpace;
        }
    },
    LEFT {
        @Override
        protected String alignString(String value, int resultLength) {
            return String.format("%-" + resultLength + "s", value);
        }
    }, RIGHT {
        @Override
        protected String alignString(String value, int resultLength) {
            return String.format("%" + resultLength + "s", value);
        }
    };

    protected abstract String alignString(String value, int resultLength);

    public String getAlignedString(String value, int resultLength) {
        if (resultLength < 1) {
            throw new FileWritingException("Wrong length for new string: " + resultLength);
        }
        String alignedValue;
        if (value.length() == resultLength) {
            alignedValue = value;
        } else if (value.length() > resultLength) {
            alignedValue = value.substring(0, resultLength);
        } else {
            alignedValue = alignString(value, resultLength);
        }
        return alignedValue;
    }
}
