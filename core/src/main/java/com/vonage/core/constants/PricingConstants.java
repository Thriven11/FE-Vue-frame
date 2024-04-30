package com.vonage.core.constants;

/**
 * Constant Class to define Pricing API related Constants.
 */
public final class PricingConstants {

    /**
     * Static class should not be initialized.
     */
    private PricingConstants() {
    }

    /**
     * The "disableSync" Constant
     */
    public static final String PN_DISABLE_SYNC = "disableSync";

    /**
     * messagingTraffic
     */
    public static final String PN_MESSAGING_TRAFFIC = "messagingTraffic";

    /**
     * monthlyFee
     */
    public static final String PN_MONTHLY_FEE = "monthlyFee";

    /**
     * features
     */
    public static final String PN_FEATURES = "features";

    /**
     * flatMobilePrice
     */
    public static final String PN_FLAT_MOBILE_PRICE = "flatMobilePrice";

    /**
     * voiceTraffic
     */
    public static final String PN_VOICE_TRAFFIC = "voiceTraffic";

    /**
     * Base Path
     */
    public static final String PRODUCTS_BASE_PATH = "/var/commerce/products/vonage/communications-api";

    /**
     * Outgoing - All
     */
    public static final String PN_OUTGOING = "outgoing/outgoing";

    /**
     * Outgoing - Mobile
     */
    public static final String PN_OUTGOING_MOBILE = "outgoing/outgoing.mobile";

    /**
     * Outgoing - Landline
     */
    public static final String PN_OUTGOING_LANDLINE = "outgoing/outgoing.landline";

    /**
     * VIRTUAL Incoming
     */
    public static final String PN_VIRTUAL_INCOMING = "virtual-number/incoming";

    /**
     * VIRTUAL Rental - All
     */
    public static final String PN_VIRTUAL_RENTAL = "virtual-number/rental";

    /**
     * VIRTUAL Rental - voice
     */
    public static final String PN_VIRTUAL_RENTAL_VOICE = "virtual-number/rental.voice";

  /**
   * VIRTUAL Rental - sms
   */
  public static final String PN_VIRTUAL_RENTAL_SMS = "virtual-number/rental.sms";

    /**
     * TOLLFREE Incoming
     */
    public static final String PN_TOLLFREE_INCOMING = "toll-free/incoming";

    /**
     * TOLLFREE Rental
     */
    public static final String PN_TOLLFREE_RENTAL = "toll-free/rental";

    /**
     * TOLLFREE Rental - voice
     */
    public static final String PN_TOLLFREE_RENTAL_VOICE = "toll-free/rental.voice";

  /**
   * TOLLFREE Rental - sms
   */
  public static final String PN_TOLLFREE_RENTAL_SMS = "toll-free/rental.sms";

    /**
     * Fixer API format
     */
    public static final String FIXER_API_FORMAT = "%s&symbols=%s";

    /**
     * USD property name
     */
    public static final String PN_USD = "USD";

    /**
     * rates property in JSON
     */
    public static final String JSON_RATES = "rates";

    /**
     * flatPrice
     */
    public static final String PN_FLAT_PRICE = "flatPrice";

    /**
     * flatPushPrice
     */
    public static final String PN_FLAT_PUSH_PRICE = "flatPushPrice";

    /**
     * flatFailPrice
     */
    public static final String PN_FLAT_FAIL_PRICE = "flatFailPrice";

    /**
     * Country CSV
     */
    public static final String COUNTRY_CSV =
      "AD,AE,AF,AG,AI,AL,AM,AO,AR,AS,AT,AU,AW,AZ,BA,BB,BD,BE,BF,BG,BH,BI,BJ,BM,BN,BO,BQ,BR,"
        + "BS,BT,BW,BY,BZ,CA,CD,CF,CG,CH,CI,CK,CL,CM,CN,CO,CR,CU,CV,CW,CY,CZ,DE,DJ,DK,DM,DO,DZ,"
        + "EC,EE,EG,ER,ES,ET,FI,FJ,FO,FR,GA,GB,GD,GE,GF,GH,GI,GL,GM,GN,GP,GQ,GR,GT,GU,GW,GY,HK,HN,"
        + "HR,HT,HU,ID,IE,IL,IN,IQ,IR,IS,IT,JM,JO,JP,KE,KG,KH,KI,KM,KN,KR,KW,KY,KZ,LA,LB,LC,LI,LK,"
        + "LR,LS,LT,LU,LV,LY,MA,MC,MD,ME,MG,MH,MK,ML,MM,MN,MO,MP,MQ,MR,MS,MT,MU,MV,MW,MX,MY,MZ,NA,"
        + "NC,NE,NG,NI,NL,NO,NP,NR,NZ,OM,PA,PE,PF,PG,PH,PK,PL,PM,PR,PS,PT,PW,PY,QA,RE,RO,RS,RU,RW,"
        + "SA,SB,SC,SD,SE,SG,SI,SK,SL,SM,SN,SO,SR,SS,ST,SV,SY,SZ,TC,TD,TG,TH,TJ,TL,TM,TN,TO,TR,TT,"
        + "TW,TZ,UA,UG,US,UY,UZ,VC,VE,VG,VI,VN,VU,WS,YE,YT,ZA,ZM,ZW";

}
