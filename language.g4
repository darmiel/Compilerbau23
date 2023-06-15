grammar language;
sumExpr: NUMBER (sumOp NUMBER)*;
sumOp: PLUS|MINUS;
NUMBER: [0-9]+;
PLUS: '+';
MINUS: '-';
WS: [ \t\r\n]+ -> skip;

andOrOp: AND|OR;
AND: '&&';
OR: '||';
andOrExpr: cmpExpr (andOrOp cmpExpr)*;