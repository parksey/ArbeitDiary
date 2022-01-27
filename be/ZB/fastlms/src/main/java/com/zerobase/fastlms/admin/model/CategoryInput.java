package com.zerobase.fastlms.admin.model;

import lombok.Data;

@Data
public class CategoryInput {
	String categoryName;
	long id;
	int sortValue;
	boolean usingYn;
}
