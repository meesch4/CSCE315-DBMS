grammar OldSqlTokens;

// Need to figure out a way to ignore

SELECT: 'SELECT' | 'select';
CREATE: 'CREATE' | 'create';
INSERT: 'INSERT INTO' | 'insert into';
RENAME: 'RENAME' | 'rename';

VARIABLE: ('a'..'z' | 'A'..'Z')+; // valid variable names. Any letter for now

// Should we separate the types into a separate lexer file?
TYPE: INTEGER | VARCHAR_NAME | CHAR | FLOAT | DATE | TIME;

INTEGER: 'INTEGER' | 'integer' | 'INT' | 'int';
FLOAT: 'FLOAT' | 'float' | 'REAL' | 'real';

DATE: 'DATE' | 'date';
TIME: 'TIME' | 'time';

/*DATE: YEAR'-'MONTH'-'DAY; // yyyy-mm-dd
YEAR: (ANY_DIGIT){4}; // 4 occurences of any number 0-9. Should 0001 be a valid year?
MONTH:
    ('0' ANY_DIGIT) | // 01 - 09
    ('1' '0'..'2'); // 10 - 12
DAY:
    ('0' '1'..'9') |       // 01 - 09
    ('1'..'2' ANY_DIGIT) | // 10 - 29
    ('30' | '31');        // 30 - 31

TIME: HOUR':'MIN_SEC':'MIN_SEC; // hh:mm:ss, 24hr time
HOUR:
    ('0'..'1' ANY_DIGIT) // 00 - 19
    ('2' '0'..'3');      // 20 - 23

MIN_SEC: '0'..'5' ANY_DIGIT; // Minute or second, 00 - 59
*/

PARAMETER: '(' (ANY_DIGIT)+ ')';

VARCHAR_NAME: 'VARCHAR' | 'varchar';
VARCHAR: VARCHAR_NAME SPACES PARAMETER;

CHAR_NAME: 'CHAR' | 'char';
CHAR: CHAR_NAME SPACES PARAMETER;

SPACES: ' '+?; // Any number of spaces, including 0. Not sure if needed
ANY_DIGIT: '0'..'9'; // Any digit 0 - 9

WS: ' ';
// WS: (' '|'\t')+ -> skip; // Skip any tabs or spaces