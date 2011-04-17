/*
 * Copyright 2008-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.alvsanand.webpage.common;

public class UAgentInfo {
    // User-Agent and Accept HTTP request headers

    private String userAgent = "";
    private String httpAccept = "";
    
    // Optional: store values for quickly accessing same info multiple times.
    // Call InitDeviceScan() to initialize these values.
    private boolean iphone = false;
    private boolean tierIphone = false;
    private boolean tierRichCss = false;
    private boolean tierGenericMobile = false;
    
    // Initialize some initial smartphone string variables.
    public static final String engineWebKit = "webkit";
    public static final String deviceAndroid = "android";
    
    public static final String deviceIphone = "iphone";
    public static final String deviceIpod = "ipod";
    public static final String deviceIpad = "ipad";
    public static final String deviceMacPpc = "macintosh"; //Used for disambiguation
    
    public static final String deviceSymbian = "symbian";
    public static final String deviceS60 = "series60";
    public static final String deviceS70 = "series70";
    public static final String deviceS80 = "series80";
    public static final String deviceS90 = "series90";
    
    public static final String deviceWinPhone7 = "windows phone os 7";
    public static final String deviceWinMob = "windows ce";
    public static final String deviceWindows = "windows";
    public static final String deviceIeMob = "iemobile";
    public static final String devicePpc = "ppc"; //Stands for PocketPC
    public static final String enginePie = "wm5 pie"; //An old Windows Mobile
    
    public static final String deviceBB = "blackberry";
    public static final String vndRIM = "vnd.rim"; //Detectable when BB devices emulate IE or Firefox
    public static final String deviceBBStorm = "blackberry95";  //Storm 1 and 2
    public static final String deviceBBBold = "blackberry97";  //Bold
    public static final String deviceBBTour = "blackberry96";  //Tour
    public static final String deviceBBCurve = "blackberry89";  //Curve 2
    public static final String deviceBBTorch = "blackberry 98";  //Torch
    
    public static final String devicePalm = "palm";
    public static final String deviceWebOS = "webos"; //For Palm's new WebOS devices
    public static final String engineBlazer = "blazer"; //Old Palm
    public static final String engineXiino = "xiino"; //Another old Palm
    
    public static final String deviceKindle = "kindle";  //Amazon Kindle, eInk one.
    
    public static final String deviceNuvifone = "nuvifone";  //Garmin Nuvifone
    
    //Initialize variables for mobile-specific content.
    public static final String vndwap = "vnd.wap";
    public static final String wml = "wml";
    
    //Initialize variables for other random devices and mobile browsers.
    public static final String deviceBrew = "brew";
    public static final String deviceDanger = "danger";
    public static final String deviceHiptop = "hiptop";
    public static final String devicePlaystation = "playstation";
    public static final String deviceNintendoDs = "nitro";
    public static final String deviceNintendo = "nintendo";
    public static final String deviceWii = "wii";
    public static final String deviceXbox = "xbox";
    public static final String deviceArchos = "archos";
    
    public static final String engineOpera = "opera"; //Popular browser
    public static final String engineNetfront = "netfront"; //Common embedded OS browser
    public static final String engineUpBrowser = "up.browser"; //common on some phones
    public static final String engineOpenWeb = "openweb"; //Transcoding by OpenWave server
    public static final String deviceMidp = "midp"; //a mobile Java technology
    public static final String uplink = "up.link";
    public static final String engineTelecaQ = "teleca q"; //a modern feature phone browser
    public static final String devicePda = "pda"; //some devices report themselves as PDAs
    public static final String mini = "mini";  //Some mobile browsers put "mini" in their names.
    public static final String mobile = "mobile"; //Some mobile browsers put "mobile" in their user agent strings.
    public static final String mobi = "mobi"; //Some mobile browsers put "mobi" in their user agent strings.
    
    //Use Maemo, Tablet, and Linux to test for Nokia"s Internet Tablets.
    public static final String maemo = "maemo";
    public static final String maemoTablet = "tablet";
    public static final String linux = "linux";
    public static final String qtembedded = "qt embedded"; //for Sony Mylo
    public static final String mylocom2 = "com2"; //for Sony Mylo also
    
    //In some UserAgents, the only clue is the manufacturer.
    public static final String manuSonyEricsson = "sonyericsson";
    public static final String manuericsson = "ericsson";
    public static final String manuSamsung1 = "sec-sgh";
    public static final String manuSony = "sony";
    public static final String manuHtc = "htc"; //Popular Android and WinMo manufacturer
    
    //In some UserAgents, the only clue is the operator.
    public static final String svcDocomo = "docomo";
    public static final String svcKddi = "kddi";
    public static final String svcVodafone = "vodafone";

    //Disambiguation strings.
    public static final String disUpdate = "update"; //pda vs. update


    /**
     * Initialize the userAgent and httpAccept variables
     *
     * @param userAgent the User-Agent header
     * @param httpAccept the Accept header
     */
    public UAgentInfo(String userAgent, String httpAccept) {
        if (userAgent != null) {
            this.userAgent = userAgent.toLowerCase();
        }
        if (httpAccept != null) {
            this.httpAccept = httpAccept.toLowerCase();
        }

        //Intialize key stored values.
        initDeviceScan();
    }

    /**
     * Return the lower case HTTP_USER_AGENT
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Return the lower case HTTP_ACCEPT
     */
    public String getHttpAccept() {
        return httpAccept;
    }

    /**
     * Return whether the device is an Iphone or iPod Touch
     */
    public boolean getIsIphone() {
        return iphone;
    }

    /**
     * Return whether the device is in the Iphone Tier.
     */
    public boolean getIsTierIphone() {
        return tierIphone;
    }

    /**
     * Return whether the device is an Iphone or iPod Touch
     */
    public boolean getIsTierRichCss() {
        return tierRichCss;
    }

    /**
     * Return whether the device is an Iphone or iPod Touch
     */
    public boolean getIsTierGenericMobile() {
        return tierGenericMobile;
    }

    /**
     * Initialize Key Stored Values.
     */
    public void initDeviceScan() {
        this.iphone = isIphoneOrIpod();
        this.tierIphone = isTierIphone();
        this.tierRichCss = isTierRichCss();
        this.tierGenericMobile = isTierOtherPhones();
    }

    /**
     * Detects if the current device is an iPhone.
     */
    public boolean isIphone() {
        // The iPad and iPod touch say they're an iPhone! So let's disambiguate.
        if (userAgent.indexOf(deviceIphone) != -1 && 
                !isIpad() && 
                !isIpod()) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an iPod Touch.
     */
    public boolean isIpod() {
        if (userAgent.indexOf(deviceIpod) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an iPad tablet.
     */
    public boolean isIpad() {
        if (userAgent.indexOf(deviceIpad) != -1
                && isWebkit()) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an iPhone or iPod Touch.
     */
    public boolean isIphoneOrIpod() {
        //We repeat the searches here because some iPods may report themselves as an iPhone, which would be okay.
        if (userAgent.indexOf(deviceIphone) != -1
                || userAgent.indexOf(deviceIpod) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an Android OS-based device.
     */
    public boolean isAndroid() {
        if (userAgent.indexOf(deviceAndroid) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an Android OS-based device and
     * the browser is based on WebKit.
     */
    public boolean isAndroidWebKit() {
        if (isAndroid() && isWebkit()) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is based on WebKit.
     */
    public boolean isWebkit() {
        if (userAgent.indexOf(engineWebKit) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is the S60 Open Source Browser.
     */
    public boolean isS60OssBrowser() {
        //First, test for WebKit, then make sure it's either Symbian or S60.
        if (isWebkit()
                && (userAgent.indexOf(deviceSymbian) != -1
                || userAgent.indexOf(deviceS60) != -1)) {
            return true;
        }
        return false;
    }

    /**
     *
     * Detects if the current device is any Symbian OS-based device,
     *   including older S60, Series 70, Series 80, Series 90, and UIQ,
     *   or other browsers running on these devices.
     */
    public boolean isSymbianOS() {
        if (userAgent.indexOf(deviceSymbian) != -1
                || userAgent.indexOf(deviceS60) != -1
                || userAgent.indexOf(deviceS70) != -1
                || userAgent.indexOf(deviceS80) != -1
                || userAgent.indexOf(deviceS90) != -1) {
            return true;
        }
        return false;
    }
    
    /**
     * Detects if the current browser is a 
     * Windows Phone 7 device.
     */
    public boolean DetectWindowsPhone7() {
        if (userAgent.indexOf(deviceWinPhone7) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is a Windows Mobile device.
     * Excludes Windows Phone 7 devices.
     * Focuses on Windows Mobile 6.xx and earlier.
     */
    public boolean isWindowsMobile() {
        //Exclude new Windows Phone 7.
        if (DetectWindowsPhone7()) {
            return false;
        }
        //Most devices use 'Windows CE', but some report 'iemobile'
        //  and some older ones report as 'PIE' for Pocket IE.
        //  We also look for instances of HTC and Windows for many of their WinMo devices.
        if (userAgent.indexOf(deviceWinMob) != -1
                || userAgent.indexOf(deviceWinMob) != -1
                || userAgent.indexOf(deviceIeMob) != -1
                || userAgent.indexOf(enginePie) != -1
                || (userAgent.indexOf(manuHtc) != -1 && userAgent.indexOf(deviceWindows) != -1) 
                || (isWapWml() && userAgent.indexOf(deviceWindows) != -1)) {
            return true;
        }
        
        //Test for Windows Mobile PPC but not old Macintosh PowerPC.
        if (userAgent.indexOf(devicePpc) != -1 &&
                                !(userAgent.indexOf(deviceMacPpc) != -1))
            return true;
            
        return false;
    }

    /**
     * Detects if the current browser is a BlackBerry of some sort.
     */
    public boolean isBlackBerry() {
        if (userAgent.indexOf(deviceBB) != -1 || httpAccept.indexOf(vndRIM) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is a BlackBerry device AND uses a
     *    WebKit-based browser. These are signatures for the new BlackBerry OS 6.
     *    Examples: Torch
     */
    public boolean DetectBlackBerryWebKit() {
        if (userAgent.indexOf(deviceBB) != -1 &&
                        userAgent.indexOf(engineWebKit) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is a BlackBerry Touch
     * device, such as the Storm or Torch
     */
    public boolean isBlackBerryTouch() {
        if (userAgent.indexOf(deviceBBStorm) != -1 ||
                        userAgent.indexOf(deviceBBTorch) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is a BlackBerry device AND
     *   has a more capable recent browser.
     *   Examples, Storm, Bold, Tour, Curve2
     *   Excludes the new BlackBerry OS 6 browser!!
     */
    public boolean isBlackBerryHigh() {
        //Disambiguate for BlackBerry OS 6 (WebKit) browser
        if (DetectBlackBerryWebKit()) 
                        return false;
        if (isBlackBerry()) {
            if (isBlackBerryTouch()
                    || userAgent.indexOf(deviceBBBold) != -1
                    || userAgent.indexOf(deviceBBTour) != -1
                    || userAgent.indexOf(deviceBBCurve) != -1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Detects if the current browser is a BlackBerry device AND
     *   has an older, less capable browser.
     *   Examples: Pearl, 8800, Curve1
     */
    public boolean isBlackBerryLow() {
        if (isBlackBerry()) {
            //Assume that if it's not in the High tier, then it's Low
            if (isBlackBerryHigh()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Detects if the current browser is on a PalmOS device.
     */
    public boolean isPalmOS() {
        //Most devices nowadays report as 'Palm', but some older ones reported as Blazer or Xiino.
        if (userAgent.indexOf(devicePalm) != -1
                || userAgent.indexOf(engineBlazer) != -1
                || userAgent.indexOf(engineXiino) != -1) {
            //Make sure it's not WebOS first
            if (isPalmWebOS()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Detects if the current browser is on a Palm device
     *    running the new WebOS.
     */
    public boolean isPalmWebOS() {
        if (userAgent.indexOf(deviceWebOS) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is a
     *    Garmin Nuvifone.
     */
    public boolean isGarminNuvifone() {
        if (userAgent.indexOf(deviceNuvifone) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Check to see whether the device is any device
     *   in the 'smartphone' category.
     */
    public boolean isSmartphone() {
        return (isIphoneOrIpod()
                || isS60OssBrowser()
                || isSymbianOS()
                || isAndroid()
                || isWindowsMobile()
                || DetectWindowsPhone7()
                || isBlackBerry()
                || isPalmWebOS()
                || isPalmOS()
                || isGarminNuvifone());
    }

    /**
     * Detects whether the device is a Brew-powered device.
     */
    public boolean isBrewDevice() {
        if (userAgent.indexOf(deviceBrew) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects the Danger Hiptop device.
     */
    public boolean isDangerHiptop() {
        if (userAgent.indexOf(deviceDanger) != -1
                || userAgent.indexOf(deviceHiptop) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects Opera Mobile or Opera Mini.
     */
    public boolean isOperaMobile() {
        if (userAgent.indexOf(engineOpera) != -1
                && (userAgent.indexOf(mini) != -1
                || userAgent.indexOf(mobi) != -1)) {
            return true;
        }
        return false;
    }

    /**
     * Detects whether the device supports WAP or WML.
     */
    public boolean isWapWml() {
        if (httpAccept.indexOf(vndwap) != -1
                || httpAccept.indexOf(wml) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an Amazon Kindle.
     */
    public boolean isKindle() {
        if (userAgent.indexOf(deviceKindle)!= -1) {
            return true;
        }
        return false;
    }

    /**
     * The quick way to is for a mobile device.
     *  Will probably is most recent/current mid-tier Feature Phones
     *  as well as smartphone-class devices. Excludes Apple iPads.
     */
    public boolean isMobileQuick() {
        //Let's say no if it's an iPad, which contains 'mobile' in its user agent
        if (isIpad()) {
            return false;
        }
        //Most mobile browsing is done on smartphones
        if (isSmartphone()) {
            return true;
        }

        if (isWapWml()) {
            return true;
        }
        if (isBrewDevice()) {
            return true;
        }
        if (isOperaMobile()) {
            return true;
        }

        if (userAgent.indexOf(engineNetfront) != -1) {
            return true;
        }
        if (userAgent.indexOf(engineUpBrowser) != -1) {
            return true;
        }
        if (userAgent.indexOf(engineOpenWeb) != -1) {
            return true;
        }

        if (isDangerHiptop()) {
            return true;
        }

        if (isMidpCapable()) {
            return true;
        }

        if (isMaemoTablet()) {
            return true;
        }
        if (isArchos()) {
            return true;
        }

        if ((userAgent.indexOf(devicePda) != -1) &&
                        (userAgent.indexOf(disUpdate) < 0)) //no index found
        {
            return true;
        }
        if (userAgent.indexOf(mobile) != -1) {
            return true;
        }

        return false;
    }

    /**
     * Detects if the current device is a Sony Playstation.
     */
    public boolean isSonyPlaystation() {
        if (userAgent.indexOf(devicePlaystation) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is a Nintendo game device.
     */
    public boolean isNintendo() {
        if (userAgent.indexOf(deviceNintendo) != -1
                || userAgent.indexOf(deviceWii) != -1
                || userAgent.indexOf(deviceNintendoDs) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is a Microsoft Xbox.
     */
    public boolean isXbox() {
        if (userAgent.indexOf(deviceXbox) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an Internet-capable game console.
     */
    public boolean isGameConsole() {
        if (isSonyPlaystation()
                || isNintendo()
                || isXbox()) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device supports MIDP, a mobile Java technology.
     */
    public boolean isMidpCapable() {
        if (userAgent.indexOf(deviceMidp) != -1
                || httpAccept.indexOf(deviceMidp) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is on one of the Maemo-based Nokia Internet Tablets.
     */
    public boolean isMaemoTablet() {
        if (userAgent.indexOf(maemo) != -1) {
            return true;
        } else if (userAgent.indexOf(maemoTablet) != -1
                && userAgent.indexOf(linux) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current device is an Archos media player/Internet tablet.
     */
    public boolean isArchos() {
        if (userAgent.indexOf(deviceArchos) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Detects if the current browser is a Sony Mylo device.
     */
    public boolean isSonyMylo() {
        if (userAgent.indexOf(manuSony) != -1
                && (userAgent.indexOf(qtembedded) != -1
                || userAgent.indexOf(mylocom2) != -1)) {
            return true;
        }
        return false;
    }

    /**
     * The longer and more thorough way to is for a mobile device.
     *   Will probably is most feature phones,
     *   smartphone-class devices, Internet Tablets,
     *   Internet-enabled game consoles, etc.
     *   This ought to catch a lot of the more obscure and older devices, also --
     *   but no promises on thoroughness!
     */
    public boolean isMobileLong() {
        if (isMobileQuick()
                || isGameConsole()
                || isSonyMylo()) {
            return true;
        }

        //is older phones from certain manufacturers and operators.
        if (userAgent.indexOf(uplink) != -1) {
            return true;
        }
        if (userAgent.indexOf(manuSonyEricsson) != -1) {
            return true;
        }
        if (userAgent.indexOf(manuericsson) != -1) {
            return true;
        }
        if (userAgent.indexOf(manuSamsung1) != -1) {
            return true;
        }

        if (userAgent.indexOf(svcDocomo) != -1) {
            return true;
        }
        if (userAgent.indexOf(svcKddi) != -1) {
            return true;
        }
        if (userAgent.indexOf(svcVodafone) != -1) {
            return true;
        }

        return false;
    }

    //*****************************
    // For Mobile Web Site Design
    //*****************************
    /**
     * The quick way to is for a tier of devices.
     *   This method iss for devices which can
     *   display iPhone-optimized web content.
     *   Includes iPhone, iPod Touch, Android, Palm WebOS, etc.
     */
    public boolean isTierIphone() {
        if (isIphoneOrIpod()
                || isAndroid()
                || isAndroidWebKit()
                || DetectWindowsPhone7()
                || DetectBlackBerryWebKit()
                || isPalmWebOS()
                || isGarminNuvifone()
                || isMaemoTablet()) {
            return true;
        }
        return false;
    }

    /**
     * The quick way to is for a tier of devices.
     *   This method iss for devices which are likely to be capable
     *   of viewing CSS content optimized for the iPhone,
     *   but may not necessarily support JavaScript.
     *   Excludes all iPhone Tier devices.
     */
    public boolean isTierRichCss() {
        //The following devices are explicitly ok.
        //Note: 'High' BlackBerry devices ONLY
        if (isMobileQuick()
                && (isWebkit()
                || isS60OssBrowser()
                || isBlackBerryHigh()
                || isWindowsMobile()
                || userAgent.indexOf(engineTelecaQ) != -1)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The quick way to is for a tier of devices.
     *   This method iss for all other types of phones,
     *   but excludes the iPhone and RichCSS Tier devices.
     */
    public boolean isTierOtherPhones() {
        if (isMobileQuick() && (!isTierIphone()) && (!isTierRichCss())) {
            return true;
        }
        return false;
    }
}