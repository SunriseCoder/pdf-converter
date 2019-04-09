class ConvertUtils {
	static convertText(sourceValue, replacementTable, result, error, progress) {
		var doNotConvert = replacementTable.doNotConvert;
		var replacements = replacementTable.replacements;
		var resultValue = '';
		for (var i = 0; i < sourceValue.length; i++) {
            var char = sourceValue.charAt(i);
			var found = false;
			if (char.match(doNotConvert)) {
				resultValue += char;
				found = true;
			}
			if (!found) {
				for (var j = 0; j < replacements.length; j++) {
					var repSrc = replacements[j][0];
					var srcSubStr = sourceValue.substr(i, repSrc.length);
					if (repSrc === srcSubStr) {
						resultValue = addComplicatedChar(resultValue, replacements[j][1]);
						i += repSrc.length - 1;
						found = true;
						break;
					}
				}
			}
			if (!found) {
				error(char, i);
				resultValue += '<font color="red">' + char + '</font>';
			}
			//console.log((i + 1) + ' of ' + sourceValue.length + ' is done');
			progress(i + 1, sourceValue.length);
		}
		result(resultValue);

		function addComplicatedChar(resultString, replacement) {
			for (var i = 0; i < replacement.length; i++) {
				var replacementChar = replacement[i];
				if (replacementChar === '\u0008' && resultString.length > 0
					&& resultString.charAt(resultString.length - 1) !== '>') {
					resultString = resultString.substring(0, resultString.length - 1);
					continue;
				}
				resultString += replacementChar;
			}
			return resultString;
		}
	}
}
