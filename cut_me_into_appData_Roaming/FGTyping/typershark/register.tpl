<HTML>

<SCRIPT LANGUAGE="JavaScript">

function SubmitData()
{	
	document.DataForm.submit();
}

setTimeout("SubmitData();", 100);

</SCRIPT>

<BODY>

<FONT SIZE=4>
<B>
<CENTER>
Please wait, opening secure registration page ...
</CENTER>
</B>
</FONT>

<FORM NAME="DataForm" ACTION="http://www.popcap.com/register.php" METHOD=POST>
<INPUT TYPE="HIDDEN" NAME="theGame" VALUE="%ProdName%">
<INPUT TYPE="HIDDEN" NAME="version" VALUE="%Version%">
<INPUT TYPE="HIDDEN" NAME="variation" VALUE="%Variation%">
<INPUT TYPE="HIDDEN" NAME="referid" VALUE="%ReferId%">
<INPUT TYPE="HIDDEN" NAME="downloadid" VALUE="%DownloadId%">
<INPUT TYPE="HIDDEN" NAME="timesplayed" VALUE="%TimesPlayed%">
<INPUT TYPE="HIDDEN" NAME="timedout" VALUE="%TimedOut%">
<INPUT TYPE="HIDDEN" NAME="stats" VALUE="%Stats%">

</FORM>

</BODY>
</HTML>