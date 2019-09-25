grammar Sql;
rename: (select | create | insert)';';

create: CREATE 'TABLE' ;

select: ;

insert: ;

// Lexer stuff
CREATE: 'CREATE' | 'create';

VARIABLE_NAME: .+ ' '; // Anything + a space at the end
