package es.edm.dao.impl;

import org.hibernate.criterion.LikeExpression;
import org.hibernate.criterion.MatchMode;

public class LikeExpressionPrayers extends LikeExpression {

    public static final Character ESCAPE_CHARACTER = '@';
    private static final long serialVersionUID = 3461069252096689259L;

    public LikeExpressionPrayers(
            String propertyName,
            String value,
            MatchMode matchMode) {

        super(propertyName, value, matchMode, ESCAPE_CHARACTER, true);

    }
}