package code.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Like predicate support.
 *
 * @author Naotsugu Kobayashi
 */
public interface LikeSupport {

    CriteriaBuilder builder();

    /** the escape character. */
    char ESCAPE_CHAR = '\\';

    /** the escape pattern should be replaced. */
    Pattern ESCAPE_PATTERN = Pattern.compile("([%_\\\\％＿])");

    /** the replacement string. */
    String REPLACEMENT = "\\\\$1";


    /**
     * Escape for like pattern.
     *
     * @param str  string to be escaped
     * @return escaped string
     */
    static String escape(String str) {
        Matcher m = ESCAPE_PATTERN.matcher(str);
        return m.replaceAll(REPLACEMENT);
    }


    /**
     * Create a predicate for testing whether the expression satisfies `right match`.
     *
     * @param expression string expression
     * @param string string for testing
     * @return like predicate
     */
    default Predicate rightMatch(Expression<String> expression, String string) {
        return builder().like(expression, "%" + escape(string), ESCAPE_CHAR);
    }


    /**
     * Create a predicate for testing whether the expression satisfies `left match`.
     *
     * @param expression string expression
     * @param string string for testing
     * @return like predicate
     */
    default Predicate leftMatch(Expression<String> expression, String string) {
        return builder().like(expression, escape(string) + "%", ESCAPE_CHAR);
    }


    /**
     * Create a predicate for testing whether the expression satisfies `partial match`.
     *
     * @param expression string expression
     * @param string string for testing
     * @return like predicate
     */
    default  Predicate partialMatch(Expression<String> expression, String string) {
        return builder().like(expression, "%" + escape(string) + "%", ESCAPE_CHAR);
    }


}
