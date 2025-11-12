package com.bank.retail.infrastructure.common;

import java.util.Set;

public class AppConstant {

    public static final String MODULE_ID = "moduleId";
    public static final String SUB_MODULE_ID = "subModuleId";
    public static final String BRANCH_CODE = "branchCode";
    public static final String CUSTOMER_NO = "customerNumber";
    public static final String ACTIVE = "ACTIVE";
    public static final String DEBIT = "DEBIT";
    public static final String PREPAID = "PREPAID";
    public static final String CARDBINVALIDATION = "CardBinValidation";
    public static final String STATUS_ACT = "ACT";
    public static final String OPEN_IN_EN = "OPEN";
    public static final String OPEN_IN_AR = "مفتوح";

    public static final String CLOSE_IN_EN = "CLOSED";
    public static final String CLOSE_IN_AR = "مغلق";

    public static final String UNKNOWN = "UNKNOWN";

    public static final String COUNTRY_IN_AR = "قطر";

    public static final String LANGUAGE_IN_AR = "ar";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String LANGUAGE_ERROR = "ACT";
    public static final String LANGUAGE_ERROR_DESC = "LANGUAGE ERROR";
    public static final String SCREEN_ID = "screenId";
    public static final String MICROSERVICE_ID = "COMMON";
    public static final String DATE_RANGE = "DATE_RANGE";
    public static final String DATE_FORMATE = "yyyy-MM-dd";
    public static final String RATE_FORMATE = "0.00";
    public static final String COUNTRY_CODE = "DOHA";
    public static final String MIDDLEWARE_SUCCESS_CODE = "0000";
    public static final String MIDDLEWARE_FAILURE_CODE = "0004";

    public static final String RESULT_CODE = "000000";
    public static final String RESULT_DESC = "SUCCESS";
    public static final String ACTIVATION_FLAG_DESC = "Active Card";
    public static final String ERROR_CODE= "G-00001";
    public static final String SUCCESS = "SUCCESS";
    public static final String NODATA = "NO DATA CONTENT";
    public static final String FAILURECODE = "000001";
    public static final String GEN_ERROR_CODE = "000500";
    public static final String GEN_ERROR_DESC = "Unable to process your request,Please contact Customer Care for futher assistance or try again later";
    public static final String INVALID_BENE_TYPE_ERROR_DESC = "Invalid beneficiary type";
    public static final String VIEW_RMPROFILE = "VIEW_RMPROFILE";
    public static final String DEFAULT_USER = "default_user";
    public static final String USER_NAME = "userName";
    public static final String CLIENT_SESSION_KEY = "clientSessionKey";
    public static final String SESSION_KEY = "sessionKey";
    public static final String BRANCH = "BRANCH";
    public static final String ATM = "ATM";
    public static final String VALIDATION_FAILURE_CODE = "000500";
    public static final String INVALID_PIN_BLOCK = "INVALID PIN BLOCK";

    public static final String VALIDATION_FAILURE_DESC = "Internal server error";
    public static final String CARD_CREATION_FAILURE_DESC = "CARD ACTIVATION FAILED";
    public static final String PIN_CREATION_FAILURE_DESC = "PIN CREATION FAILED";
    public static final String KIOSK = "KIOSK";
    public static final String BRANCHES_IN_AR = "فروع";
    public static final String KIOSKS_IN_AR = "أكشاك";
    public static final String ATMS_IN_AR = "إيه تي إم إس";


    public static final String BRANCHES = "branches";
    public static final String ATMS = "atms";
    public static final String KIOSKS = "kiosks";
    public static final Set<String> SUPPORTED_LANGUAGES = Set.of("en", "ar");
    public static final String IMAGE = "image";
    public static final String ZONE_ID = "Asia/Qatar";
    public static final String PUBLIC_URL = "/public";

    public static final String HEADER_UNIT = "unit";

    public static final String HEADER_CHANNEL = "channel";

    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";

    public static final String SERVICEID = "serviceId";

    public static final String HEADER_GUID = "guid";

    public static final String SERVICE_ID = "serviceId";

    public static final String BROWSER = "browser";

    public static final String USER_NO = "userNo";

    public static final String IP_ADDRESS = "ipAddress";

    public static final String CUSTOMER_ID = "customerId";

    public static final String URL = "url";

    public static final String RSA_CIPHR_ALG = "RSA/ECB/PKCS1Padding";

    public static final int PBKDF2_ITERATION = 100000;

    public static final int PBKDF2_KEY_SIZE = 128;

    public static final String PBKDF2_SECRET_ALGORITHM = "PBKDF2WithHmacSHA512";

    public static final String RSA_KEY_ALG = "RSA";

    public static final int RSA_KEY_SIZE = 2048;


    public static final String CHANNEL_MB = "MB";

    public static final String IB_OCS_ENC_REQ = "IB_OCS_ENC_REQ";

    public static final String RP_SERVICE_ID = "RP";

    public static final String LOGIN_SERVICE_ID = "LOGIN";


    public static final String ACT = "000000";

    public static final String NO_DATA_CODE = "000404";
    public static final String ERROR_DATA_CODE = "000400";
    public static final String USER_BLOCKED = "000411";
    public static final String USER_BLOCKED_DATA_MSG = "User blocked by bank";
    public static final String CARD_MESSAGE = "CARD_NOT_VALID_MUST_USE_DEBIT";
    public static final String INVALID_BENE_TYPE = "Invalid beneficiary type Try('Within Own Account','Within Dukhan','Within Qatar','Cardless Withdrawal','International Transfer','Western Union','Standing Order')";
    public static final String BIN_VALIDATE_DATA_MSG = "Incorrect card details";
    public static final String PIN_ENCRYPT_DATA_MSG = "PIN_ENCRYPTION_FAILED";
    public static final String RETRY_DATA_CODE = "000419";
    public static final String RETRY_DATA_MSG = "Retry after 24hrs";
    public static final String USER_NOT_FOUND_CODE = "000421";
    public static final String CARD_INACTIVE_CODE = "000424";
    public static final String CARD_INACTIVE_MSG = "Card Not Active";
    public static final String USER_NOT_FOUND_MSG = "Username not available";
    public static final String OTP_LIMIT= "000413";
    public static final String OTP_GENERATE= "000414";
    public static final String INNER_SERVICE= "000503";
    public static final String OTP_LIMIT_MSG= "OTP blocked for max failure attempts";
    public static final String INNER_SERVICE_MSG= "Service Unavailable";
    public static final String OTP_GENERATE_MSG= "OTP Generation failed";
    public static final String INVALID_ATTAMPTS_CODE = "000418";
    public static final String INVALID_ATTAMPTS_MSG = "Max invalid attempts reached for card validation";
    public static final String DCARD_SERVICE = "DCARD.PIN.VERIFICATION";
    public static final String CARDINFO_SERVICE = "PCS.CARD.INFO";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String PIN = "pin";
    public static final String DEFAULT_UNIT = "DEFAULT";
    public static final String DEFAULT_CHANNEL = "WEB";
    public static final String DEFAULT_SERVICEID = "OTP_SERVICE";
    public static final String DEFAULT_SCREENID = "LOGIN_SCREEN";
    public static final String DEFAULT_MODULEID = "AUTH_MODULE";
    public static final String DEFAULT_SUNMODULEID = "OTP_SUBMODULE";
    public static final String LOCKED = "LOCKED";
    public static final String BLOCKED = "BLOCKED";
    public static final String INVALID = "INACTIVE";
    public static final String NEW_PIN = "newPINBlock";

    public static final String OTP_FORGET = "forget";
    public static final String MWRESULT_CODE = "0";
    public static final String PIN_CREATION_SERVICE = "PCS.NEW.PIN.CREATION";
    public static final String CARD_ACTIVATION_SERVICE = "PCS.CARD.ACTIVATION";
    public static final String SOAP_RESULT_CODE = "0";

    public static final String ACCOUNT = "0";



    public static final String CARD_LENGTH_ERROR_CODE = "000400";

    public static final String CARD_LENGTH_ERROR_DESC = "Card number must be at least 16 digits";
    public static final String CARD_PIN_LENGTH_ERROR_DESC = "Card pin must be at least 4 digits";



    public static final String GLOBAL_ID = "globalId";
    public static final String GUID = "guid";
    public static final String AUID = "auid";

    public static final String FUNCTIONAL_ID = "functionalId";
    public static final String INQUIRY_TYPE = "inquiryType";
    public static final String UNIT_ID = "unitId";
    public static final String IS_DELEGATED_ACCOUNT = "isDelegateAccount";
    public static final String MEDIA_TYPE="application/json";
    public static final String PREFERRED_UNIT = "preferredUnit";
    public static final String LANG = "lang";
    public static final String CHANNEL_ID="channelId";
    public static final String IBAN_PATH = "/iban";
    public static final String ACCOUNT_REQ_PATH = "/acc";
    public static final String DEPOSIT_REQ_PATH = "/deposit";
    public static final String DETAIL_REQ_PATH = "/detail";
    public static final String ACC_PAGE_PATH = "/page";
    public static final String ACC_LIST_FX_PATH = "/list-fx";
    public static final String STATEMENT_REQ_PATH = "/statement";
    public static final String STATEMENT_ACC_LIST_PATH = "/acc-list";
    public static final String GLOBAL_ACC_SUMMARY = "globalAccSummary";
    public static final String ACCOUT_PAGE_RES_SCHEMA = """
				{
				"status": {
					"code": "000000",
					"description": "SUCCESS"
				},
				"data": {
					"unit": {
						"id": "PRD",
						"name": "Qatar",
						"currency": "QAR",
						"iban": "Y",
						"unitTotal": ""988,999,929,850.46",
						"accounts": [],
						"savingAccs": [],
						"deposits": [],
						"esavingAccs": []
					}
				}
			}""";
    public static final String ACCOUNT_STMT_ACC_LIST = """
				{
				"status": {
					"code": "000000",
					"description": "SUCCESS"
				},
				"data": {
					"units": [{}]
				}
			}""";

    public static final String DEFAULT_UNIT_ID = "PRD";

    public static final String IBAN_RES = "{"
            + " \"iban\": \"QA50QNBA000000000066657858001\","
            + " \"accountNumber\": \"1234567890\","
            + " \"accountType\": \"CURRENT ACCOUNT\","
            + " \"customerType\": \"EA\","
            + " \"currency\": \"QAR\","
            + " \"shortName\": \"AC1234567890\","
            + " \"bankId\": \"\","
            + " \"country\": \"Qatar\","
            + " \"location\": \"\","
            + " \"branchId\": \"\","
            + " \"swiftCode\": \"QNBAQAQAXXX\""
            + "}";

    public static final String FX_RES = """
			{
			"status": {
				"code": "000000",
				"description": "SUCCESS"
			},
			"data": {
				"units": [{}]
			}
		}""";

    public static final String UNIT = "unit";

    public static final String FUNCTION_ID = "functionId";


    public static final String DATETIME = "dateTime";

    public static final String ENCRYPTED = "Encrypted";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String ENVIROMENT = "Environment";

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CURRENCY = "currency";

    public static final String IBAN = "iban";

    public static final String UNIT_TOTAL = "unitTotal";

    public static final String SUMMARY_PATH = "/summary";

    public static final String ACCOUNT_SUMMARY = """
		    {
	        "status": {
	            "code": "000000",
	            "description": "SUCCESS"
	        },
	        "data": {
	            "balance": ,
	            "ccy":,
	            "acNo": ,
	            "txnDetails": [
	                {
	                    
	                }
	            ]
	        }
	    }
	    """;




    public static final String SUCCESS_CODE = "200";

    public static final String ACCOUNT_SUMMARY_EXAMPLE_OBJECT_VALUE = """			
			{
		    "status": {
		        "code": "000000",
		        "description": "SUCCESS"
		    },
		    "data": {
		        "totalAvailableBalance": {
		            "QAR": "400,000.00",
		            "EUR": "10,000.00",
		            "USD": "20,000.00"
		        },
		        "accountDetails": [
		            {
		                "accountNum": "5675763245000743323",
		                "availableBal": "55646.87",
		                "currencyName": "Qatari Riyal",
		                "accountType": "Current Accounts",
		                "accountTypeFlag": "3",
		                "currentBal": "55656.9",
		                "acctFormat": "0202-1207003-001-0010-000"
		            },
		            {
		                "accountNum": "5675763245000743323",
		                "availableBal": "55646.87",
		                "currencyName": "Qatari Riyal",
		                "accountType": "Current Accounts",
		                "accountTypeFlag": "3",
		                "currentBal": "55656.9",
		                "acctFormat": "0202-1207003-001-0010-000"
		            }
		        ]
		    }
		}""";

    public static final String TOTAL_AVAILABLE_BALANCE = "totalAvailableBalance";

    public static final String ACCOUNT_DETAILS = "accountDetails";

    public static final String STATEMENT_PATH = "/statement";

    public static final String ACCOUNT_STATEMENT_EXAMPLE_OBJECT_VALUE = "";



    public static final String ACCOUNT_STATEMENT_EXPORT = """
			  {
			     "status": {
			         "code": "000000",
			         "description": "SUCCESS"
			     },
			     "data": {
					"fileData": "base64 content",
					"name": "File Name",
					"fileType": "File type(.pdf/.xlsx)"
			     }
			 }
			 """;

    public static final String MINI = "MINI";

    public static final String STANDARD = "STANDARD";

    public static final String NO_DATA = "no_data";

    public static final String NOT_FOUND = "no_found";


    public static final String ACCOUNT_NUM = "accountNum";


    public static final String OTP_REF_NO = "otpRefNo";

    public static final String ACC_DETAILS = "/details";
    public static final String ACCOUNT_DETAILS_NOT_FOUND = "AD-000001";
    public static final String ACC_DETAILS_EXAMPLE_OBJECT_VALUE = """
						{
			    "status": {
			        "code": "000000",
			        "description": "SUCCESS"
			    },
			    "data": {
			        "accountDetails": {
			            "dateOpen": "2020-01-01",
			            "availableBal": "900.00",
			            "ledgerName": "Main Ledger",
			            "currentBal": "1000.00",
			            "currencyName": "USD",
			            "accountName": "John Doe",
			            "iban": "US29NWBK60161331926819",
			            "accountNumber": "5675763245000743323"
			        }
			    }
			}""";

    public static final String CUST_TYPE = "custType";

    public static final String PDF = "PDF";

    public static final String OTP_FLAG = "N";

    public static final String ACTION_FLAG = "D";

    public static final String ACTIVE_FLAG = "Y";

    public static final String ACTIVE_TIME = "6";

    public static final String DEPOSIT_SUM_ALLOWED_LEDGER = "DEPOSIT_SUM_ALLOWED_LEDGER";

    public static final String DEPOSIT_SUMMARY = "depositSummary";

    public static final String DEPOSIT_DETAIL = "depositDetail";

    public static final String TOTAL_AVAIL_BALANCE = "totalAvailBalance";

    public static final String MW_DATE_FORMAT_DDMMYYY = "ddMMyyyy";

    public static final String DD_MMM_YYYY = "dd-MMM-yyyy";

    public static final String CURRENCY_NAME = "currencyName";

    public static final String CURRENCY_CODE = "currencyCode";

    public static final String CURRENT_ACCOUNT = "Current Account";

    public static final String CURRENT_ACCOUNTS = "Current Accounts";

    public static final String ACCOUNT_TYPE = "accountType";

    public static final String CURRENCY_FULL_NAME = "currency";

    public static final String E_STATEMENT ="/estatement";

    public static final String ESTATEMENT_FORMAT = "/format";

    public static final String ESTATEMENT_FREQUENCY = "/frequency";

    public static final String ESTATEMENT_CONFIRM = "/confirm";

    public static final String ESTATEMENT_SUMMARY ="/summary";

    public static final String FORMAT ="ESTMT_FORMAT";

    public static final String ESTATEMENT_ACC_LIST ="/accList";

    public static final String ESTMT_ACTION_FLAG_ADD = "I";

    public static final String ESTMT_ACTION_FLAG_MODIFY = "U";

    public static final String ESTMT_FORMAT_FREQ = "ESTMT_FORMAT_FREQ";

    public static final String FREQUENCY = "ESTMT_FREQUENCY";

    public static final String ESTMT_ID = "estatementId";

    public static final String SUMMARY_ANALYTICS_PATH = "/analytics/summary";

    public static final String EXCLUDE_ACC = "EXCLUDE_ACC";


    public static final String NOT_FOUND_CODE = "000404";
    public static final String NOT_FOUND_DESC = "No Data Found";

    public static final String SERVICE_UNAVAILABLE_CODE = "000503";
    public static final String SERVICE_UNAVAILABLE_DESC = "Service Unavailable";

    public static final String REQUEST_TIMEOUT_CODE = "000408";
    public static final String REQUEST_TIMEOUT_DESC = "Request Timeout";

    public static final String DUPLICATE_REQUEST_CODE = "000409";
    public static final String DUPLICATE_REQUEST_DESC = "Duplicate request";

    public static final String BAD_REQUEST_CODE = "000400";
    public static final String BAD_REQUEST_DESC = "Bad Request";

    public static final String DEVICE_INFO_DESC = "Device Info Not Found";
    public static final String MANDATORY_HEADERS_DESC = "Mandatory Headers not Found";

    public static final String REQUEST_INFO_DESC="Request Info Not Found";


}
