package com.mwaltman.devops.framework.util;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Group of utilities for modifying and formatting strings.
 */
public class StringUtils {

    /**
     * Two spaces
     */
    public static final String WS_2_SPACES  = "  ";

    /**
     * Four spaces
     */
    public static final String WS_4_SPACES  = WS_2_SPACES + WS_2_SPACES;

    /**
     * Twenty-six spaces
     */
    public static final String WS_26_SPACES = WS_4_SPACES + "                      ";

    /**
     * Twenty-eight spaces
     */
    public static final String WS_28_SPACES = WS_2_SPACES + WS_26_SPACES;

    /**
     * Caption for use in pretty-formatting suppressed exceptions
     */
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";

    /**
     * Caption for use in pretty-formatting caused by exceptions
     */
    private static final String CAUSE_CAPTION = "Caused by: ";

    /**
     * Caption for use in pretty-formatting exceptions which self-reference
     */
    private static final String CIRCULAR_REFERENCE = "[CIRCULAR REFERENCE: ";

    /**
     * Pretty-format an exception enclosed in another exception.
     *
     * @param enclosedThrowable Throwable which is enclosed in another exception
     * @param enclosingTrace Stack trace of the throwable that encloses the
     *                       enclosed exception
     * @param caption The caption (explanation) of the enclosed exception
     * @param perLinePrefix Prefix to use immediately after every newline
     * @param throwables The current list of throwables that are associated with
     *                   (and including) the top-most enclosing exception
     *
     * @return A pretty-formatted string containing all the details of the
     * enclosed exception
     */
    private static String prettyEnclosedThrowable(Throwable enclosedThrowable,
                                           StackTraceElement[] enclosingTrace,
                                           String caption,
                                           String perLinePrefix,
                                           Set<Throwable> throwables) {
        if (throwables.contains(enclosedThrowable)) {
            return perLinePrefix + CIRCULAR_REFERENCE + enclosedThrowable + "]";
        }

        throwables.add(enclosedThrowable);

        /* Compute number of frames in common between this and enclosing trace */
        StackTraceElement[] enclosedTrace = enclosedThrowable.getStackTrace();
        int enclosedTraceIndex = enclosedTrace.length - 1;
        int enclosingTraceIndex = enclosingTrace.length - 1;
        while (enclosedTraceIndex >= 0
                && enclosingTraceIndex >= 0
                && enclosedTrace[enclosedTraceIndex]
                    .equals(enclosingTrace[enclosingTraceIndex])) {
            enclosedTraceIndex--;
            enclosingTraceIndex--;
        }
        int commonFramesCount = enclosedTrace.length - 1 - enclosedTraceIndex;

        StringBuilder stringBuilder = new StringBuilder()
                .append(perLinePrefix)
                .append(caption)
                .append(enclosedThrowable)
                .append("\n");

        for (int i = 0; i <= enclosedTraceIndex; i++) {
            stringBuilder.append(perLinePrefix)
                    .append("\tat ")
                    .append(enclosedTrace[i])
                    .append("\n");
        }

        if (commonFramesCount != 0) {
            stringBuilder.append(perLinePrefix)
                    .append("\t... ")
                    .append(commonFramesCount)
                    .append(" more\n");
        }

        for (Throwable aSuppressedThrowable : enclosedThrowable.getSuppressed()) {
            stringBuilder.append(prettyEnclosedThrowable(
                    aSuppressedThrowable,
                    enclosedTrace,
                    SUPPRESSED_CAPTION,
                    perLinePrefix + "\t",
                    throwables));
        }

        Throwable cause = enclosedThrowable.getCause();
        if (cause != null) {
            stringBuilder.append(prettyEnclosedThrowable(
                    cause,
                    enclosedTrace,
                    CAUSE_CAPTION,
                    perLinePrefix,
                    throwables));
        }

        return stringBuilder.toString();
    }

    /**
     * Pretty-format an exception and any enclosed (suppressed or caused by)
     * exceptions.
     *
     * @param throwable The exception to pretty-format
     * @param perLinePrefix Prefix to use immediately after every newline
     *
     * @return A pretty-formatted string containing all the details of the
     * exception and any and all enclosed exceptions
     */
    public static String prettyException(Throwable throwable, String perLinePrefix) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();

        Set<Throwable> throwables = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
        throwables.add(throwable);

        StringBuilder stringBuilder = new StringBuilder()
                .append(perLinePrefix)
                .append(throwable)
                .append("\n");

        for (StackTraceElement aStackTraceElement : stackTrace) {
            stringBuilder.append(perLinePrefix)
                    .append("\tat ")
                    .append(aStackTraceElement)
                    .append("\n");
        }

        for (Throwable aSuppressedThrowable : throwable.getSuppressed()) {
            stringBuilder.append(prettyEnclosedThrowable(
                    aSuppressedThrowable,
                    stackTrace,
                    SUPPRESSED_CAPTION,
                    perLinePrefix + "\t",
                    throwables));
        }

        Throwable cause = throwable.getCause();
        if (cause != null) {
            stringBuilder.append(prettyEnclosedThrowable(
                    cause,
                    stackTrace,
                    CAUSE_CAPTION,
                    perLinePrefix,
                    throwables));
        }

        return stringBuilder.toString();
    }
}
