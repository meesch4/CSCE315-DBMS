grammar test;
prule: (select | create | insert)';'; // Must end with a semi-colon
select: 'SELECT';

create: 'CREATE' createType '(' columnNames ')' 'PRIMARY_KEY' primaryKeys;
createType: 'TABLE';
primaryKeys: 'primaryKeys';

insert: 'INSERT INTO ' tableName ' '? 'VALUES FROM ' '(' tableValues ')';
tableName: 'regex' ' '; // Regex here for tableNames? Don't care what the name is as long as it has valid characters
tableValues: ' '? ('regex' | '4' | 'regex' ',' tableValues) ' '?;

columnNames: 'regex' type | 'regex' type ',';
type: 'type';
