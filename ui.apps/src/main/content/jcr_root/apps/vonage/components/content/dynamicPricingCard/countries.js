"use strict";
use(function() {
  const countries = [
    {
      code: "AF",
      key: "vng-AF"
    },
    {
      code: "AL",
      key: "vng-AL"
    },
    {
      code: "DZ",
      key: "vng-DZ"
    },
    {
      code: "AS",
      key: "vng-AS"
    },
    {
      code: "AD",
      key: "vng-AD"
    },
    {
      code: "AO",
      key: "vng-AO"
    },
    {
      code: "AI",
      key: "vng-AI"
    },
    {
      code: "AG",
      key: "vng-AG"
    },
    {
      code: "AR",
      key: "vng-AR"
    },
    {
      code: "AM",
      key: "vng-AM"
    },
    {
      code: "AW",
      key: "vng-AW"
    },
    {
      code: "AU",
      key: "vng-AU"
    },
    {
      code: "AT",
      key: "vng-AT"
    },
    {
      code: "AZ",
      key: "vng-AZ"
    },
    {
      code: "BS",
      key: "vng-BS"
    },
    {
      code: "BH",
      key: "vng-BH"
    },
    {
      code: "BD",
      key: "vng-BD"
    },
    {
      code: "BB",
      key: "vng-BB"
    },
    {
      code: "BY",
      key: "vng-BY"
    },
    {
      code: "BE",
      key: "vng-BE"
    },
    {
      code: "BZ",
      key: "vng-BZ"
    },
    {
      code: "BJ",
      key: "vng-BJ"
    },
    {
      code: "BM",
      key: "vng-BM"
    },
    {
      code: "BT",
      key: "vng-BT"
    },
    {
      code: "BO",
      key: "vng-BO"
    },
    {
      code: "BQ",
      key: "vng-BQ"
    },
    {
      code: "BA",
      key: "vng-BA"
    },
    {
      code: "BW",
      key: "vng-BW"
    },
    {
      code: "BR",
      key: "vng-BR"
    },
    {
      code: "VG",
      key: "vng-VG"
    },
    {
      code: "BN",
      key: "vng-BN"
    },
    {
      code: "BG",
      key: "vng-BG"
    },
    {
      code: "BF",
      key: "vng-BF"
    },
    {
      code: "BI",
      key: "vng-BI"
    },
    {
      code: "KH",
      key: "vng-KH"
    },
    {
      code: "CM",
      key: "vng-CM"
    },
    {
      code: "CA",
      key: "vng-CA"
    },
    {
      code: "CV",
      key: "vng-CV"
    },
    {
      code: "KY",
      key: "vng-KY"
    },
    {
      code: "CF",
      key: "vng-CF"
    },
    {
      code: "TD",
      key: "vng-TD"
    },
    {
      code: "CL",
      key: "vng-CL"
    },
    {
      code: "CN",
      key: "vng-CN"
    },
    {
      code: "CO",
      key: "vng-CO"
    },
    {
      code: "KM",
      key: "vng-KM"
    },
    {
      code: "CK",
      key: "vng-CK"
    },
    {
      code: "CR",
      key: "vng-CR"
    },
    {
      code: "HR",
      key: "vng-HR"
    },
    {
      code: "CU",
      key: "vng-CU"
    },
    {
      code: "CW",
      key: "vng-CW"
    },
    {
      code: "CY",
      key: "vng-CY"
    },
    {
      code: "CZ",
      key: "vng-CZ"
    },
    {
      code: "CD",
      key: "vng-CD"
    },
    {
      code: "DK",
      key: "vng-DK"
    },
    {
      code: "DJ",
      key: "vng-DJ"
    },
    {
      code: "DM",
      key: "vng-DM"
    },
    {
      code: "DO",
      key: "vng-DO"
    },
    {
      code: "TL",
      key: "vng-TL"
    },
    {
      code: "EC",
      key: "vng-EC"
    },
    {
      code: "EG",
      key: "vng-EG"
    },
    {
      code: "SV",
      key: "vng-SV"
    },
    {
      code: "GQ",
      key: "vng-GQ"
    },
    {
      code: "ER",
      key: "vng-ER"
    },
    {
      code: "EE",
      key: "vng-EE"
    },
    {
      code: "ET",
      key: "vng-ET"
    },
    {
      code: "FO",
      key: "vng-FO"
    },
    {
      code: "FJ",
      key: "vng-FJ"
    },
    {
      code: "FI",
      key: "vng-FI"
    },
    {
      code: "FR",
      key: "vng-FR"
    },
    {
      code: "GF",
      key: "vng-GF"
    },
    {
      code: "PF",
      key: "vng-PF"
    },
    {
      code: "GA",
      key: "vng-GA"
    },
    {
      code: "GM",
      key: "vng-GM"
    },
    {
      code: "GE",
      key: "vng-GE"
    },
    {
      code: "DE",
      key: "vng-DE"
    },
    {
      code: "GH",
      key: "vng-GH"
    },
    {
      code: "GI",
      key: "vng-GI"
    },
    {
      code: "GR",
      key: "vng-GR"
    },
    {
      code: "GL",
      key: "vng-GL"
    },
    {
      code: "GD",
      key: "vng-GD"
    },
    {
      code: "GP",
      key: "vng-GP"
    },
    {
      code: "GU",
      key: "vng-GU"
    },
    {
      code: "GT",
      key: "vng-GT"
    },
    {
      code: "GN",
      key: "vng-GN"
    },
    {
      code: "GW",
      key: "vng-GW"
    },
    {
      code: "GY",
      key: "vng-GY"
    },
    {
      code: "HT",
      key: "vng-HT"
    },
    {
      code: "HN",
      key: "vng-HN"
    },
    {
      code: "HK",
      key: "vng-HK"
    },
    {
      code: "HU",
      key: "vng-HU"
    },
    {
      code: "IS",
      key: "vng-IS"
    },
    {
      code: "IN",
      key: "vng-IN"
    },
    {
      code: "ID",
      key: "vng-ID"
    },
    {
      code: "IE",
      key: "vng-IE"
    },
    {
      code: "IL",
      key: "vng-IL"
    },
    {
      code: "IT",
      key: "vng-IT"
    },
    {
      code: "CI",
      key: "vng-CI"
    },
    {
      code: "JM",
      key: "vng-JM"
    },
    {
      code: "JP",
      key: "vng-JP"
    },
    {
      code: "JO",
      key: "vng-JO"
    },
    {
      code: "KZ",
      key: "vng-KZ"
    },
    {
      code: "KE",
      key: "vng-KE"
    },
    {
      code: "KI",
      key: "vng-KI"
    },
    {
      code: "XK",
      key: "vng-XK"
    },
    {
      code: "KW",
      key: "vng-KW"
    },
    {
      code: "KG",
      key: "vng-KG"
    },
    {
      code: "LA",
      key: "vng-LA"
    },
    {
      code: "LV",
      key: "vng-LV"
    },
    {
      code: "LB",
      key: "vng-LB"
    },
    {
      code: "LS",
      key: "vng-LS"
    },
    {
      code: "LR",
      key: "vng-LR"
    },
    {
      code: "LY",
      key: "vng-LY"
    },
    {
      code: "LI",
      key: "vng-LI"
    },
    {
      code: "LT",
      key: "vng-LT"
    },
    {
      code: "LU",
      key: "vng-LU"
    },
    {
      code: "MO",
      key: "vng-MO"
    },
    {
      code: "MK",
      key: "vng-MK"
    },
    {
      code: "MG",
      key: "vng-MG"
    },
    {
      code: "MW",
      key: "vng-MW"
    },
    {
      code: "MY",
      key: "vng-MY"
    },
    {
      code: "MV",
      key: "vng-MV"
    },
    {
      code: "ML",
      key: "vng-ML"
    },
    {
      code: "MT",
      key: "vng-MT"
    },
    {
      code: "MH",
      key: "vng-MH"
    },
    {
      code: "MQ",
      key: "vng-MQ"
    },
    {
      code: "MR",
      key: "vng-MR"
    },
    {
      code: "MU",
      key: "vng-MU"
    },
    {
      code: "YT",
      key: "vng-YT"
    },
    {
      code: "MX",
      key: "vng-MX"
    },
    {
      code: "FM",
      key: "vng-FM"
    },
    {
      code: "MD",
      key: "vng-MD"
    },
    {
      code: "MC",
      key: "vng-MC"
    },
    {
      code: "MN",
      key: "vng-MN"
    },
    {
      code: "ME",
      key: "vng-ME"
    },
    {
      code: "MS",
      key: "vng-MS"
    },
    {
      code: "MA",
      key: "vng-MA"
    },
    {
      code: "MZ",
      key: "vng-MZ"
    },
    {
      code: "MM",
      key: "vng-MM"
    },
    {
      code: "NA",
      key: "vng-NA"
    },
    {
      code: "NR",
      key: "vng-NR"
    },
    {
      code: "NP",
      key: "vng-NP"
    },
    {
      code: "NL",
      key: "vng-NL"
    },
    {
      code: "NC",
      key: "vng-NC"
    },
    {
      code: "NZ",
      key: "vng-NZ"
    },
    {
      code: "NI",
      key: "vng-NI"
    },
    {
      code: "NE",
      key: "vng-NE"
    },
    {
      code: "NG",
      key: "vng-NG"
    },
    {
      code: "MP",
      key: "vng-MP"
    },
    {
      code: "NO",
      key: "vng-NO"
    },
    {
      code: "OM",
      key: "vng-OM"
    },
    {
      code: "PK",
      key: "vng-PK"
    },
    {
      code: "PW",
      key: "vng-PW"
    },
    {
      code: "PS",
      key: "vng-PS"
    },
    {
      code: "PA",
      key: "vng-PA"
    },
    {
      code: "PG",
      key: "vng-PG"
    },
    {
      code: "PY",
      key: "vng-PY"
    },
    {
      code: "PE",
      key: "vng-PE"
    },
    {
      code: "PH",
      key: "vng-PH"
    },
    {
      code: "PL",
      key: "vng-PL"
    },
    {
      code: "PT",
      key: "vng-PT"
    },
    {
      code: "PR",
      key: "vng-PR"
    },
    {
      code: "QA",
      key: "vng-QA"
    },
    {
      code: "CG",
      key: "vng-CG"
    },
    {
      code: "RE",
      key: "vng-RE"
    },
    {
      code: "RO",
      key: "vng-RO"
    },
    {
      code: "RU",
      key: "vng-RU"
    },
    {
      code: "RW",
      key: "vng-RW"
    },
    {
      code: "KN",
      key: "vng-KN"
    },
    {
      code: "LC",
      key: "vng-LC"
    },
    {
      code: "PM",
      key: "vng-PM"
    },
    {
      code: "VC",
      key: "vng-VC"
    },
    {
      code: "WS",
      key: "vng-WS"
    },
    {
      code: "SM",
      key: "vng-SM"
    },
    {
      code: "ST",
      key: "vng-ST"
    },
    {
      code: "SA",
      key: "vng-SA"
    },
    {
      code: "SN",
      key: "vng-SN"
    },
    {
      code: "RS",
      key: "vng-RS"
    },
    {
      code: "SC",
      key: "vng-SC"
    },
    {
      code: "SL",
      key: "vng-SL"
    },
    {
      code: "SG",
      key: "vng-SG"
    },
    {
      code: "SX",
      key: "vng-SX"
    },
    {
      code: "SK",
      key: "vng-SK"
    },
    {
      code: "SI",
      key: "vng-SI"
    },
    {
      code: "SB",
      key: "vng-SB"
    },
    {
      code: "SO",
      key: "vng-SO"
    },
    {
      code: "ZA",
      key: "vng-ZA"
    },
    {
      code: "KR",
      key: "vng-KR"
    },
    {
      code: "SS",
      key: "vng-SS"
    },
    {
      code: "ES",
      key: "vng-ES"
    },
    {
      code: "LK",
      key: "vng-LK"
    },
    {
      code: "SD",
      key: "vng-SD"
    },
    {
      code: "SR",
      key: "vng-SR"
    },
    {
      code: "SZ",
      key: "vng-SZ"
    },
    {
      code: "SE",
      key: "vng-SE"
    },
    {
      code: "CH",
      key: "vng-CH"
    },
    {
      code: "TW",
      key: "vng-TW"
    },
    {
      code: "TJ",
      key: "vng-TJ"
    },
    {
      code: "TZ",
      key: "vng-TZ"
    },
    {
      code: "TH",
      key: "vng-TH"
    },
    {
      code: "TG",
      key: "vng-TG"
    },
    {
      code: "TO",
      key: "vng-TO"
    },
    {
      code: "TT",
      key: "vng-TT"
    },
    {
      code: "TN",
      key: "vng-TN"
    },
    {
      code: "TR",
      key: "vng-TR"
    },
    {
      code: "TM",
      key: "vng-TM"
    },
    {
      code: "TC",
      key: "vng-TC"
    },
    {
      code: "VI",
      key: "vng-VI"
    },
    {
      code: "UG",
      key: "vng-UG"
    },
    {
      code: "UA",
      key: "vng-UA"
    },
    {
      code: "AE",
      key: "vng-AE"
    },
    {
      code: "GB",
      key: "vng-GB"
    },
    {
      code: "US",
      key: "vng-US"
    },
    {
      code: "UY",
      key: "vng-UY"
    },
    {
      code: "UZ",
      key: "vng-UZ"
    },
    {
      code: "VU",
      key: "vng-VU"
    },
    {
      code: "VE",
      key: "vng-VE"
    },
    {
      code: "VN",
      key: "vng-VN"
    },
    {
      code: "YE",
      key: "vng-YE"
    },
    {
      code: "ZM",
      key: "vng-ZM"
    },
    {
      code: "ZW",
      key: "vng-ZW"
    }
  ]
  return countries;
});
