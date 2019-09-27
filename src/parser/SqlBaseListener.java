package parser;

import parser.antlr.*;
import dbms.*;

// From following the "Guide - ANTLR4 Listener Setup" here: https://docs.google.com/document/d/1kCv5ODfAh8HZZ8uugMLcqMqJLvs9RQDmhvBGt3l2Fzo/edit
public class SqlBaseListener extends SQLGrammarBaseListener {
    private Dbms dbms;

    public SqlBaseListener() {
        this.dbms = new Dbms();
    }
}
