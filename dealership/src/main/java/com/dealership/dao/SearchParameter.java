package com.dealership.dao;

public class SearchParameter {
	public enum Operator {
		AND("AND"), //
		OR("OR");

		private String name;

		private Operator(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum ValueOperator {
		EQUALS("="), //
		NOT_EQUALS("!="), //
		LESSER_THAN("<"), //
		LESSER_THAN_OR_EQUAL_TO("<="), //
		GREATER_THAN(">"), //
		GREATER_THAN_OR_EQUAL_TO(">=");

		private String name;

		private ValueOperator(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private String fieldName;
	private String value;
	private Operator operator;
	private ValueOperator valueOperator;

	public String getFieldName() {
		return fieldName;
	}

	public String getValue() {
		return value;
	}

	public Operator getOperator() {
		return operator;
	}

	public ValueOperator getValueOperator() {
		return valueOperator;
	}

	public SearchParameter(String fieldName, String value, Operator operator, ValueOperator valueOperator) {
		super();
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
		this.valueOperator = valueOperator;
	}
}
