var replacementTables = [
	{
		comment: 'Bhagavad-Gita diacritics',
		doNotConvert: '[а-я \\-\/’]', 
		replacements: [
			['\n', '<br />'],
			['h', 'р\u0323'], ['f', 'а\u0304'], ['n', 'т\u0323'], ['x', 'х\u0323'], ['t', 'н\u0323'], ['l', 'д\u0323'], ['i', 'ш\u0341'],
			['u', 'н\u0342'], ['v', 'м\u0307'], ['b', 'и\u0304']
		]
	},{
		comment: 'Bhagavad-Gita sanscrit',
		doNotConvert: '[ ]', 
		replacements: [
			// System
			['\n', '<br />'], ['-', ''],

			// 5 Chars
			['YaaeR', 'र्यो'],

			// 4 Chars
			['JauR', 'र्जु'], ['ZYae', 'ष्ये'],
			['iNTa', 'न्ति'],

			// 3 Chars
			['í\\', 'ष्ट्र'],
			['BYa', 'ब्य'], ['MYa', 'म्य'], ['Qa|', 'र्थं'],
			['SMa', 'स्म'], ['STa', 'स्त'], ['SYa', 'स्य'], ['TSa', 'त्स'], ['VYa', 'व्य'],
			['iDa', 'धि'], ['iJa', 'जि'], ['iMa', 'मि'], ['iXa', 'शि'],
			['MaR', 'र्म'], ['YaR', 'र्य'],

			// 2 Chars
			// Ligaturs
			['+a', 'क्ष'], ['<@', 'ण्ड'], ['}a', 'त्र'],
			['Tk', 'त्क'], ['ta', 'त्त'],
			['ik', 'कि'], ['iv', 'वि'], ['ià', 'न्नि'], ['iÜ', 'द्वि'],
			['vR', 'र्व'],
			// Letters
			['))', '॥'], ['<a', 'ण'], ['>a', 'भ'], ['$=', 'ट'],
			['Da', 'ध'], ['Ja', 'ज'], ['Ma', 'म'], ['Na', 'न'], ['Pa', 'प'], ['Qa', 'थ'], ['Sa', 'स'], ['Ta', 'त'], ['Xa', 'श'], ['Ya', 'य'],

			// 1 Char
			// Ligaturs
			['®', 'ङ्ग'], [']', '्र'], ['§', 'क्र'],
			['N', 'न्'], ['j', 'ज्ञ'],
			['Å', 'ञ्ज'], ['à', 'न्न'], ['å', 'रु'], ['ê', 'श्च'], ['î', 'ष्ट्व'], ['í', 'ष्ट'], ['Ü', 'द्व'],
			// Letters
			['(', '्'], [')', '।'], ['"', 'ः'], ['&', 'ं'], ['!', 'ढ'], ['*', 'ृ'],
			['0', '०'], ['1', '१'], ['2', '२'], ['3', '३'], ['4', '४'], ['5', '५'], ['6', '६'], ['7', '७'], ['8', '८'], ['9', '९'],
			['A', 'अ'], ['E', 'ै'], ['U', 'ू'], ['W', 'ए'], ['X', 'श्'], ['Z', 'ष्'],
			['a', 'ा'], ['b', 'ब'], ['c', 'च'], ['d', 'द'], ['e', 'े'], ['h', 'ह'],
			['k', 'क'], ['o', 'उ'], ['q', 'ी'], ['r', 'र'], ['u', 'ु'], ['v', 'व'],
		]
	},{
		comment: 'Devanagari to Diacritics English',
		doNotConvert: '[, \-]',
		replacements: [
			// System
			['\n', '<br />'],

			// Vowels
			['अ', 'a'], ['आ', 'ā'], ['ा', '̄'],
			['ु', '\u0008u'],
			['ऊ', 'ū'], ['ू', '\u0008ū'],
			['इ', 'i'], ['ि', '\u0008i'],
			['ई', '\u012b'], ['ी', '\u0008\u012b'],
			['ृ', '\u0008r\u0323'],
			['े', '\u0008e'], ['ै', 'i'],
			['ो', '\u0008o'], ['औ', 'au'], ['ौ', 'u'],

			// Consonats
			
			// Gutturals
			['क', 'ka'], ['ख', 'kha'], ['ग', 'ga'],
			// Palatals
			['च', 'ca'], ['छ', 'cha'], ['ज', 'ja'], ['झ', 'jha'],
			// Cerebrals
			['ट', 'ṭa'], ['ठ', 'ṭha'], ['ड', 'ḍa'], ['ढ', 'ḍha'], ['ण', 'ṇa'],
			// Dentals
			['त', 'ta'], ['थ', 'tha'], ['द', 'da'], ['ध', 'dha'], ['न', 'na'],
			// Labials
			['प', 'pa'], ['फ', 'pha'], ['ब', 'ba'], ['भ', 'bha'], ['म', 'ma'],
			// Semivowels
			['य', 'ya'], ['र', 'ra'], ['ल', 'la'], ['व', 'va'],
			// Sibilants
			['श', 's\u0341a'], ['ष', 'ṣa'], ['स', 'sa'],
			// Aspirate
			['ह', 'ha'],

			// Irregular Consonats
			['ड़', 'ṛa'],

			// Special
			['्', '\u0008'], ['ँ', '' /* ??? (Chandra-Bindu) */], ['ं', 'ṇ'],
			['ः', 'ḥ'],
			['।', ''], ['|', ''], ['॥', ''],
			['१', '1']
		]
	},{
		comment: 'Gitabase.com russian diacritics',
		doNotConvert: '[0-9А-Яа-яӣӯ,;:. \-\(\)]',
		replacements: [
			// System
			['\n', '<br />'], ['-', ''], ['—', '-'],
			
			// Russian chars with diacritics
			['', 'а̄'],
			['', 'м̇'],
			['', 'н̣'],
			['', 'р̣'],
			['', 'х̣'],
			['', 'ш\u0341']
		]
	}
]
