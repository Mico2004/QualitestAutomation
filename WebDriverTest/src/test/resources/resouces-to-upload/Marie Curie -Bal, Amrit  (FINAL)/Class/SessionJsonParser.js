/***************************************************************\
|* FILE: SessionJsonParser.js																	 *|
|* DESC: Parses the session js file.													 *|
|*																														 *|
|* AUTH: Harel Gruia																					 *|
|* CONTRIBUTORS:																							 *|
|* DATE: 09/02/2014																						 *|
|*									Copyright Tegrity Ltd 2014								 *|
\***************************************************************/

//////////////////////////////////////////////////////////////////////////////////
//													class SessionJsonParser													    //
//													-----------------------									            //
// Use:																												                  //
//																														                  //
//////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////
//			Constructors
////////////////////////////////////////////////////////////
function SessionJsonParser(oSessionData)
{
	// Call the actual Constructor.
	if( arguments.length > 0 )
		this.Init(oSessionData);
} // SessionJsonParser
	
////////////////////////////////////////////////////////////
//											Methods
////////////////////////////////////////////////////////////
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.Init = function(oSessionData) 
{
	if(!(this.bIsValid = (typeof(oSessionData) == "object" && typeof(oSessionData["HEAD"]) == "object")))
	{
		alert("SessionJsonParser.Init(oSessionData), Missing or Invalid Recording config file.");
		return;	
	}

	this.oSessionData				= oSessionData;
	this.sLayoutFolder			= null;
	this.sPlayerJSFilePath	= null;
	this.sWebSvcFilePath		= null;
	this.oProperties				= null;
	this.oTLSegments				= null;
	this.oControls					= null;
	this.sLastError					= null;
} // SessionJsonParser.Init
	
//-----------------------------------------------------------------------
function LoadScripts(oScriptUriArr,fnLoaded)
{
  if(!oScriptUriArr.length) 
		fnLoaded && fnLoaded();
  else
	{
    for(var oScriptArr=[], i=oScriptUriArr.length; i--;)
		{
			var oScript		= document.createElement('script');
			oScript.src		= oScriptUriArr[i];
			oScript.type	= 'text/javascript';

			if(fnLoaded)
			{
				oScriptArr.push(oScript);
				oScript.onload = AreAllLoaded; 
				oScript.onreadystatechange = AreAllLoaded; // For IE8 or earlier. 
			}
		
			document.getElementsByTagName('head')[0].appendChild(oScript);
		} // for
  }

  function AreAllLoaded()
	{
    if(this.readyState === undefined || this.readyState === 'complete')
		{
      // Pull the script tags out based on the actual element in case IE ever
      // intermingles the onload and onreadystatechange handlers for the same
      // script block before notifying for another one.
      for(var j = oScriptArr.length; j--;) 
				if(oScriptArr[j] == this) oScriptArr.splice(j,1);
      if(!oScriptArr.length) fnLoaded();
    }
  } // AreAllLoaded
} // LoadScripts

//-----------------------------------------------------------------------
SessionJsonParser.prototype.toString = function() 
{
	return "I'm a SessionJsonParser";
} // SessionJsonParser.toString
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetPlayerJSFile = function() 
{
	if(this.sPlayerJSFilePath == null) 
	  this.sPlayerJSFilePath = this.oSessionData["HEAD"]["PLAYER"]["@src"];

	if(navigator.userAgent.indexOf("Firefox") != -1) // FF
	{
		// First we will check if path is relative.
		if( !((this.sPlayerJSFilePath.indexOf(":") < 0) && (this.sPlayerJSFilePath.substring(0, 1) != "//")) )
			if( !(new RegExp("^(http|https|file)\://")).test(this.sPlayerJSFilePath) )
				this.sPlayerJSFilePath = "file:///" + this.sPlayerJSFilePath;
	} // if

	return this.sPlayerJSFilePath;
} // SessionJsonParser.GetPlayerJSFile
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetLayoutFolder = function() 
{
	if(this.sLayoutFolder == null) 
	  this.sLayoutFolder = this.oSessionData["HEAD"]["LAYOUT"]["@src"] + "/";
		
	if(navigator.userAgent.indexOf("Firefox") != -1) // FF
	{
		// First we check if it's a relative path.
		if( !((this.sLayoutFolder.indexOf(":") < 0) && (this.sLayoutFolder.substring(0, 1) != "//")) )
			if( !(new RegExp("^(http|https|file)\://")).test(this.sLayoutFolder) )
				this.sLayoutFolder = "file:///" + this.sLayoutFolder;
	}

	return this.sLayoutFolder;
} // SessionJsonParser.GetLayoutFolder

//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetWebServiceFile = function() 
{
	try
	{
		// "SERVER" attribute is optional.
		if(this.sWebSvcFilePath == null) 
			this.sWebSvcFilePath = this.oSessionData["HEAD"]["SERVER"]["@src"];
	}
	catch(e){} 
	
	return this.sWebSvcFilePath;
} // SessionJsonParser.GetLayoutFolder

//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetProperties = function() 
{
	if(this.oProperties == null)
	{
		this.oProperties = new Object();
		var oPropList = this.oSessionData["HEAD"]["PROPS"];
		for(var i=0; i < oPropList.length; i++)
		{
			var name	= oPropList[i]["@name"];
			var value = oPropList[i]["@value"];
			this.oProperties[name] = value;
		}
	}
	return this.oProperties;
} // SessionJsonParser.GetProperties
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetTLSegments = function()
{
	if(this.oTLSegments == null)
	{
		this.oTLSegments = new Array();
		var nIndex,sType,nTimelineID,nTime,nDuration;
		var oTLSegmentList = this.oSessionData["TMLN"]["SGMTS"];
			
		for(var i=0; i<oTLSegmentList.length; i++)
		{
			nIndex			= oTLSegmentList[i]["@inx"];
			sType				= oTLSegmentList[i]["@type"];
			nTimelineID = oTLSegmentList[i]["@tmln-id"];
			nTime				= oTLSegmentList[i]["@time"];
			nDuration		= oTLSegmentList[i]["@dur"];
				
			// Set defaults.
			if(!IsString(nTimelineID)) nTimelineID = "0";
				
			// Add to the Arrary.
			this.oTLSegments.push({nIndex: Number(nIndex), sType: sType, nTimelineID: Number(nTimelineID),  nTime: Number(nTime), nDuration: Number(nDuration)});
		} // for 
	} // if
	return this.oTLSegments;
} // SessionJsonParser.GetTLSegments
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetControls = function() 
{
	if(this.oControls == null)
	{
		this.oControls = new Object();
		var oControlList = this.oSessionData["DATA"]["CTRLS"];
		for(var i=0; i<oControlList.length; i++)
		{
			sControlId = oControlList[i]["@id"];
			this.oControls[sControlId] = new Array();
			var oLayerList = oControlList[i]["LAYERS"];
			for(var j=0; j<oLayerList.length; j++)
			{
				oLayer = new Object(); 
				oLayer.zOrder		= oLayerList[j]["@z-order"];
				oLayer.streamId = oLayerList[j]["@stream-id"];
				oLayer.src			= oLayerList[j]["@src"];

				this.oControls[sControlId].push(oLayer);  
			}
		}  
	}
	return this.oControls;
} // SessionJsonParser.GetControls
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.CacheFile = function()
{
	this.GetPlayerJSFile();
	this.GetLayoutFolder();
	this.GetPropertiesArr();
	this.GetTLSegments();
	this.GetControls();
} // SessionJsonParser.CacheFile
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.IsValid = function()
{
	if(this.GetPlayerJSFile() == null || this.GetPlayerJSFile() == "")
	{
		this.sLastError = "No 'src' attribute in the '//SESSION/HEAD/PLAYER' node.";
		return false;
	}
	if(this.GetLayoutFolder() == null || this.GetLayoutFolder() == "")
	{
		this.sLastError = "No 'src' attribute in the '//SESSION/HEAD/LAYOUT' node.";
		return false;
	}
	if(this.GetProperties()["Duration"] == null || this.GetProperties()["Duration"] == "")
	{
		this.sLastError = "No 'Duration' property in the '//SESSION/HEAD/PROPS/PROP' node.";
		return false;
	}
	if(this.GetProperties()["SessionDateTime"] == null || this.GetProperties()["SessionDateTime"] == "")
	{
		this.sLastError = "No 'SessionDateTime' property in the '//SESSION/HEAD/PROPS/PROP' node.";
		return false;
	}
	if(this.GetProperties()["TimeZone"] == null || this.GetProperties()["TimeZone"] == "")
	{
		this.sLastError = "No 'TimeZone' property in the '//SESSION/HEAD/PROPS/PROP' node.";
		return false;
	}
	if(this.GetProperties()["SessionGUID"] == null || this.GetProperties()["SessionGUID"] == "")
	{
		this.sLastError = "No 'SessionGUID' property in the '//SESSION/HEAD/PROPS/PROP' node.";
		return false;
	}
	if(this.GetProperties()["CourseGUID"] == null || this.GetProperties()["CourseGUID"] == "")
	{
		this.sLastError = "No 'CourseGUID' property in the '//SESSION/HEAD/PROPS/PROP' node.";
		return false;
	}
	if(this.GetProperties()["InstructorGUID"] == null || this.GetProperties()["InstructorGUID"] == "")
	{
		this.sLastError = "No 'InstructorGUID' property in the '//SESSION/HEAD/PROPS/PROP' node.";
		return false;
	}
	return true;
} // SessionJsonParser.IsValid
	
//-----------------------------------------------------------------------
SessionJsonParser.prototype.GetLastError = function() 
{
	return this.sLastError;
} // SessionJsonParser.GetLastError