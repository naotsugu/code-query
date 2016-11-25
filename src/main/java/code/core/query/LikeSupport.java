package code.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface LikeSupport {

    CriteriaBuilder builder();

    char ESCAPE_CHAR = '\\';
    Pattern ESCAPE_PATTERN = Pattern.compile("([%_\\\\％＿])");
    String REPLACEMENT = "\\\\$1";


    static String escape(String str) {
        Matcher m = ESCAPE_PATTERN.matcher(str);
        return m.replaceAll(REPLACEMENT);
    }

    /**
     * Create a predicate for testing whether the expression
     * satisfies `right match`.
     * @param expression string expression
     * @param string string for testing
     * @return like predicate
     */
    default Predicate rightMatch(Expression<String> expression, String string) {
        return builder().like(expression, "%" + escape(string), ESCAPE_CHAR);
    }


    /**
     * Create a predicate for testing whether the expression
     * satisfies `left match`.
     * @param expression string expression
     * @param string string for testing
     * @return like predicate
     */
    default Predicate leftMatch(Expression<String> expression, String string) {
        return builder().like(expression, escape(string) + "%", ESCAPE_CHAR);
    }


    /**
     * Create a predicate for testing whether the expression
     * satisfies `partial match`.
     * @param expression string expression
     * @param string string for testing
     * @return like predicate
     */
    default  Predicate partialMatch(Expression<String> expression, String string) {
        return builder().like(expression, "%" + escape(string) + "%", ESCAPE_CHAR);
    }


}
