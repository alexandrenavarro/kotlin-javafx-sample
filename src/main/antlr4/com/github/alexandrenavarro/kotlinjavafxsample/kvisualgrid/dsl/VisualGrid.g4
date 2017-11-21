grammar VisualGrid;

cell : (horizontalAlign verticalAlign)? verticalAlign? horizontalAlign? control size? ('+' rowSpan)? ('+' rowSpan '+' columSpan)? ('=' groupButton)? (',' control ('=' groupButton)? )*;

control : '$' identifier;

verticalAlign : ('>' | '|' | '<');

horizontalAlign : ('_' | '-' | '^');

size : ('>' | '|' | '<');

rowSpan : DEC_DIGIT | '*';

columSpan : DEC_DIGIT | '*';

groupButton : DEC_DIGIT | ;

identifier : (LETTER | '_') (LETTER | '_' | DEC_DIGIT)*;

LETTER : [a-zA-Z]+;

DEC_DIGIT : [0-9]+;
