/*
 * Copyright (c) 2014, NET MARKS COMPANY, LIMITED All Rights Reserved.
 *
 * JsonDateSerializer.java
 *
 * @date 2013/08/01
 * @version 1.0
 * @author KSC Tomomichi Iwasawa
 */
package jp.co.ksc.spring;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * jacksonを使用してJSON形式にする場合のフォーマットを指定するクラスです
 *
 * &lt;MODIFICATION HISTORY&gt;
 * 1.0 2013/08/01 KSC Tomomichi Iwasawa 新規作成
 * </pre>
 *
 * @author KSC Tomomichi Iwasawa
 * @version 1.0 2013/08/01
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);

	}

}