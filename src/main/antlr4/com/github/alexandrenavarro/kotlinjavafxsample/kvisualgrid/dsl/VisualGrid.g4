grammar VisualGrid;

columnConstraints : columnConstraint (spaces columnConstraint)*;

rowConstraints : cellConstraint (spaces cellConstraint)* (spaces rowConstraint)?;

rowConstraint : '[' verticalAlign? (' ' sizes)? (' ' verticalSize)? ']';

columnConstraint : '[' horizontalAlign? (' ' sizes)? (' ' horizontalSize)? ']';

cellConstraint : align? control size? ('+' rowSpan)? ('+' rowSpan '+' columSpan)? ('=' groupButtonId)? (',' control size? ('=' groupButtonId)? )*;

control : '$' identifier;

size : (verticalSize horizontalSize) | (horizontalSize verticalSize) | verticalSize | horizontalSize;

align : (verticalAlign horizontalAlign) | (horizontalAlign verticalAlign) | verticalAlign | horizontalAlign;

verticalAlign : ('_' | '-' | '^');

horizontalAlign : ('>' | '|' | '<');

verticalSize : ('_' | '-' | '^');

horizontalSize : ('>' | '|' | '<');

rowSpan : DEC_DIGIT | '*';

columSpan : DEC_DIGIT | '*';

groupButtonId : DEC_DIGIT | ;

identifier : (LETTER | '_') (LETTER | '_' | DEC_DIGIT)*;

sizes : (prefSize | minSize '::' | minSize ':' prefSize ':' | minSize ':' prefSize ':' maxSize | ':' prefSize ':' maxSize | '::' maxSize | ':' prefSize ':' ) ;

spaces : ' '+;

minSize : DEC_DIGIT;

prefSize : DEC_DIGIT;

maxSize : DEC_DIGIT;

LETTER : [a-zA-Z]+;

DEC_DIGIT : [0-9]+;
