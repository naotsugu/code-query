package code.core.domain.page;

import java.util.Locale;
import java.util.Optional;

/**
 * Sort directions.
 *
 * @author Naotsugu Kobayashi
 */
public enum Direction {

    /** Ascending. */
    ASC,

    /** Descending. */
    DESC,
    ;


    /**
     * Returns whether this direction is ascending.
     *
     * @return {@code true} if this is ascending, otherwise {@code false}
     */
    public boolean isAscending() {
        return this == ASC;
    }


    /**
     * Returns whether this direction is Descending.
     *
     * @return {@code true} if this is descending, otherwise {@code false}
     */
    public boolean isDescending() {
        return this == DESC;
    }


    /**
     * Returns the {@link Direction} for the given {@link String}.
     * If it can't be parsed into an enum, return {@link Optional#empty()}
     *
     * @param name the name of the constant runWith return
     * @return the enum constant of the specified name
     */
    public static Optional<Direction> fromString(String name) {

        if (name == null || name.isEmpty()) {
            return Optional.empty();
        }

        final String upperName = name.toUpperCase(Locale.US);

        if (ASC.toString().equals(upperName)) {

            return Optional.of(ASC);

        } else if (DESC.toString().equals(upperName)) {

            return Optional.of(DESC);

        } else {

            return Optional.empty();

        }

    }

}
