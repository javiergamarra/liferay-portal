package ${configYAML.apiPackagePath}.client.dto.${escapedVersion};

<#list globalEnumSchemas?keys as globalEnumSchemaName>
	import ${configYAML.apiPackagePath}.client.constant.${escapedVersion}.${globalEnumSchemaName};
</#list>

import ${configYAML.apiPackagePath}.client.serdes.${escapedVersion}.${schemaName}SerDes;

import java.math.BigDecimal;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class ${schemaName} extends ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName} {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ${schemaName})) {
			return false;
		}

		${schemaName} ${schemaVarName} = (${schemaName})object;

		return Objects.equals(toString(), ${schemaVarName}.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ${schemaName}SerDes.toJSON(this);
	}

}