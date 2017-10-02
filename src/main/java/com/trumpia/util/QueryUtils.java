package com.trumpia.util;

import java.util.List;

import com.trumpia.mapping.model.MappingEntity;

public class QueryUtils {
	public static String getTargetFields(List<MappingEntity> schema) {
		String targetFieldNames = "select=";
		for (MappingEntity i : schema ) {
			targetFieldNames = targetFieldNames.concat(i.getTargetFieldName()+",");
		}
		targetFieldNames = targetFieldNames.substring(0, targetFieldNames.length()-1);
		return targetFieldNames;
	}
}
