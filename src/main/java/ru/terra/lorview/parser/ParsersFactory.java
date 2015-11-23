package ru.terra.lorview.parser;

import ru.terra.lorview.parser.impl.HtmlUnitParserImpl;

public class ParsersFactory {
	public static Parser getParser() {
		return new HtmlUnitParserImpl();
	}
}
