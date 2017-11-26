grammar VisualGrid;

columnConstraints : columnConstraint * (' ' (' ')* columnConstraint)*;

rowConstraints : cellConstraint * (' ' (' ')* cellConstraint)* (' ' (' ')* rowConstraint)?;

rowConstraint : '[' verticalAlign? (' ' sizes)? (' ' verticalSize)? ']';

columnConstraint : '[' horizontalAlign? (' ' sizes)? (' ' horizontalSize)? ']';

cellConstraint : (horizontalAlign verticalAlign)? verticalAlign? horizontalAlign? control (horizontalSize verticalSize)? verticalSize? horizontalSize? ('+' rowSpan)? ('+' rowSpan '+' columSpan)? ('=' groupButtonId)? (',' control (horizontalSize verticalSize)? verticalSize? horizontalSize? ('=' groupButtonId)? )*;

control : '$' identifier;

verticalAlign : ('_' | '-' | '^');

horizontalAlign : ('>' | '|' | '<');

verticalSize : ('_' | '-' | '^');

horizontalSize : ('>' | '|' | '<');

rowSpan : DEC_DIGIT | '*';

columSpan : DEC_DIGIT | '*';

groupButtonId : DEC_DIGIT | ;

identifier : (LETTER | '_') (LETTER | '_' | DEC_DIGIT)*;

sizes : (prefSize | minSize '::' | minSize ':' prefSize ':' | minSize ':' prefSize ':' maxSize | ':' prefSize ':' maxSize | '::' maxSize) ;

minSize : DEC_DIGIT;

prefSize : DEC_DIGIT;

maxSize : DEC_DIGIT;

LETTER : [a-zA-Z]+;

DEC_DIGIT : [0-9]+;
