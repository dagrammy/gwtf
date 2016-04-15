test = {

//  template = 'xQueryTemplate.js'
  url = "http://localhost/test.htm"
	
  setUp = "casper.echo('setting'); casper.echo('it up');"
  tearDown = "casper.echo('See ya');"
  
 steps = [
	screenshot(),
	exists("#container"),
	existsXpath('//div[@id="container"]'),
	exists("#btn1"),
	fieldHasValue("textfield2", "blaaaaaaa"),
	fieldByCssHasValue("#txtFld2", "blaaaaaaa"),
	existsXpath('//input[@type="text" and @id="txtFld2"]'),
	fieldByXpathHasValue('//input[@type="text" and @id="txtFld2"]', "blaaaaaaa"),
	isDisabled("#txtFldDisabled"),
	isReadOnly("#txtFldReadonly"),
	isEnabled("#txtFld"),
	isNotReadOnly("#txtFld"),
	hasText("#visibleSpan", "Visible Span"),
	hasTextXpath('//span[@id="visibleSpan"]', "Visible Span"),
	doesntHaveText("#visibleSpan", "some Visible Span text bla"),
	doesntHaveTextXpath('//span[@id="visibleSpan"]', "some Visible Span text bla"),
	eval("return  getTextFieldValue() == 'blaaaaaaa';"),
	evalEquals("return getTextFieldValue()", "blaaaaaaa"),
	evaluateJs("setTextFieldValue();"),
	fieldHasValue("textfield4", "setTextFieldValue"),
	resourceExists("driver-forklift-safety.gif"),
	sendKeyToSelector("#txtFld2", "A"),
	screenshot(),
	fieldByCssHasValue("#txtFld2", "A"),
	sendKeysToSelector("#txtFld2", "BC"),
	screenshot(),
	fieldByCssHasValue("#txtFld2", "ABC"),
	sendKey("F6"),
	fieldByCssHasValue("#txtFldDisabled", "F6 was pressed"),
	click("#btn1"),
	screenshot(),
	exists("#container"),
	back(),
	clickXpath('//button[@id="btn1"]'),
	screenshot(),
	isVisible("#visibleSpan"),
	isNotVisible("#invisibleSpan"),
	elementCount("li", 3),
	back(),
	clickHyperlinkWithText("Klick"),
	isVisibleXpath('//span[@id="visibleSpan"]'),
	isNotVisibleXpath('//span[@id=invisibleSpan"]'),
	elementCountXpath('//li', 3),
	textExists("asdlkjasld"),
	log("test log debug"),
	info("info message"),
 ]

}
