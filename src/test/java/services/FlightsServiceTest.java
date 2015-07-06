package services;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.*;
import com.hax.models.AirportResponse;
import com.hax.models.Trip;
import com.hax.models.User;
import com.hax.models.fb.FbVerify;
import com.hax.services.TripsService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 5/2/15.
 */
public class FlightsServiceTest extends GenericTest {


    private String getMockResponse(){
        return "{\n" +
                " \"items\": [\n" +
                "  {\n" +
                "   \"id\": \"prism_AR_0_FLIGHTS_A-1_C-0_I-0_RT-BUEMIA20150821-MIABUE20150829_xorigin-api_channel-site!0!C_VV5dFqJH5c0000000038847186_VV5dFqJH5c000000001c7c430c_VV5dFqJH5c000000007b6831fd_VV5dFqJH5c000000002d967209_VV5dFqJH5cffffffffcdc0f24a_VV5dFqJH5cffffffffcca415a2_VV5dFqJH5cffffffff98a945d0_VV5dFqJH5c00000000067d5f05_VV5dFqJH5cffffffff9ccec8ff_VV5dFqJH5cffffffffdc5a8e64_VV5dFqJH5cffffffffe9e52975_VV5dFqJH5cffffffffb8d6f779_VV5dFqJH5cffffffffe7488de1_VV5dFqJH5cffffffff86cfe4f8_VV5dFqJH5cffffffffb5417b60_VV5dFqJH5c000000001efcd5ef_VV5dFqJH5c000000006a37af68_VV5dFqJH5cffffffffade8f7fc_VV5dFqJH5cffffffffcb405f67_VV5dFqJH5c000000004cf69b95_VV5dFqJH5cffffffff9f4f5be2!2,2_2,1_1,6_2,5_1,7_2,6_1,4_2,3_3,1_1,5_2,4_3,2_1,2_3,3_1,3_2,7_3,4_3,5_1,1_3,6_3,7\",\n" +
                "   \"outbound_choices\": [\n" +
                "    {\n" +
                "     \"choice\": 1,\n" +
                "     \"duration\": \"27:20\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"AEP\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-21T16:45:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-21T18:08:00.000-03:00\",\n" +
                "       \"duration\": \"01:23\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1514\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"738\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"MIA\",\n" +
                "       \"departure_datetime\": \"2015-08-22T11:05:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-22T19:05:00.000-04:00\",\n" +
                "       \"duration\": \"09:00\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1306\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 1,\n" +
                "       \"equipment_code\": \"343\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 2,\n" +
                "     \"duration\": \"26:20\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"AEP\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-21T17:45:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-21T19:08:00.000-03:00\",\n" +
                "       \"duration\": \"01:23\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"operated_by\": \"AU\",\n" +
                "       \"flight_id\": \"AR2508\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"E90\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"MIA\",\n" +
                "       \"departure_datetime\": \"2015-08-22T11:05:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-22T19:05:00.000-04:00\",\n" +
                "       \"duration\": \"09:00\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1306\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 1,\n" +
                "       \"equipment_code\": \"343\"\n" +
                "      }\n" +
                "     ],\n" +
                "     \"delay_info\": [\n" +
                "      {\n" +
                "       \"flight_id\": \"AU2508\",\n" +
                "       \"from\": \"AEP\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"on_time\": 12.5,\n" +
                "       \"cancelled\": 0,\n" +
                "       \"more_than30_minutes\": 37.5,\n" +
                "       \"more_than60_minutes\": 50,\n" +
                "       \"provider\": \"FS\",\n" +
                "       \"category\": \"undefined\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 3,\n" +
                "     \"duration\": \"23:45\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"AEP\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-21T20:20:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-21T21:43:00.000-03:00\",\n" +
                "       \"duration\": \"01:23\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1538\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"738\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"MIA\",\n" +
                "       \"departure_datetime\": \"2015-08-22T11:05:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-22T19:05:00.000-04:00\",\n" +
                "       \"duration\": \"09:00\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1306\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 1,\n" +
                "       \"equipment_code\": \"343\"\n" +
                "      }\n" +
                "     ]\n" +
                "    }\n" +
                "   ],\n" +
                "   \"inbound_choices\": [\n" +
                "    {\n" +
                "     \"choice\": 1,\n" +
                "     \"duration\": \"12:30\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T06:55:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T08:10:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1507\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"738\"\n" +
                "      }\n" +
                "     ],\n" +
                "     \"delay_info\": [\n" +
                "      {\n" +
                "       \"flight_id\": \"AR1507\",\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"on_time\": 58.06,\n" +
                "       \"cancelled\": 6.45,\n" +
                "       \"more_than30_minutes\": 29.03,\n" +
                "       \"more_than60_minutes\": 6.45,\n" +
                "       \"provider\": \"FS\",\n" +
                "       \"category\": \"undefined\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 2,\n" +
                "     \"duration\": \"15:20\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T09:45:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T11:00:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1501\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"738\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 3,\n" +
                "     \"duration\": \"15:30\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T09:55:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T11:10:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"operated_by\": \"AU\",\n" +
                "       \"flight_id\": \"AR2521\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"E90\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 4,\n" +
                "     \"duration\": \"16:43\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T11:08:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T12:23:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"operated_by\": \"AU\",\n" +
                "       \"flight_id\": \"AR2505\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"E90\"\n" +
                "      }\n" +
                "     ],\n" +
                "     \"delay_info\": [\n" +
                "      {\n" +
                "       \"flight_id\": \"AU2505\",\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"on_time\": 50,\n" +
                "       \"cancelled\": 1.61,\n" +
                "       \"more_than30_minutes\": 38.71,\n" +
                "       \"more_than60_minutes\": 9.68,\n" +
                "       \"provider\": \"FS\",\n" +
                "       \"category\": \"undefined\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 5,\n" +
                "     \"duration\": \"18:50\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T13:15:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T14:30:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"operated_by\": \"AU\",\n" +
                "       \"flight_id\": \"AR2525\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"E90\"\n" +
                "      }\n" +
                "     ],\n" +
                "     \"delay_info\": [\n" +
                "      {\n" +
                "       \"flight_id\": \"AU2525\",\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"on_time\": 39.13,\n" +
                "       \"cancelled\": 0,\n" +
                "       \"more_than30_minutes\": 34.79,\n" +
                "       \"more_than60_minutes\": 26.09,\n" +
                "       \"provider\": \"FS\",\n" +
                "       \"category\": \"undefined\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 6,\n" +
                "     \"duration\": \"22:30\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T16:55:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T18:10:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1529\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"738\"\n" +
                "      }\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"choice\": 7,\n" +
                "     \"duration\": \"25:25\",\n" +
                "     \"segments\": [\n" +
                "      {\n" +
                "       \"from\": \"MIA\",\n" +
                "       \"to\": \"COR\",\n" +
                "       \"departure_datetime\": \"2015-08-29T18:40:00.000-04:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T04:30:00.000-03:00\",\n" +
                "       \"duration\": \"08:50\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"flight_id\": \"AR1307\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 7,\n" +
                "       \"equipment_code\": \"330\"\n" +
                "      },\n" +
                "      {\n" +
                "       \"from\": \"COR\",\n" +
                "       \"to\": \"AEP\",\n" +
                "       \"departure_datetime\": \"2015-08-30T19:50:00.000-03:00\",\n" +
                "       \"arrival_datetime\": \"2015-08-30T21:05:00.000-03:00\",\n" +
                "       \"duration\": \"01:15\",\n" +
                "       \"airline\": \"AR\",\n" +
                "       \"operated_by\": \"AU\",\n" +
                "       \"flight_id\": \"AR2509\",\n" +
                "       \"cabin_type\": \"economy\",\n" +
                "       \"stopovers\": [],\n" +
                "       \"seats_remaining\": 4,\n" +
                "       \"equipment_code\": \"E90\"\n" +
                "      }\n" +
                "     ]\n" +
                "    }\n" +
                "   ],\n" +
                "   \"price_detail\": {\n" +
                "    \"commercial_policy\": \"4970\",\n" +
                "    \"currency\": \"ARS\",\n" +
                "    \"total\": 10821,\n" +
                "    \"adult_base\": 6852,\n" +
                "    \"adults_subtotal\": 6852,\n" +
                "    \"fees\": 0,\n" +
                "    \"taxes\": 3969,\n" +
                "    \"taxes_detail\": [\n" +
                "     {\n" +
                "      \"code\": \"res_3450_afip\",\n" +
                "      \"amount\": 2399\n" +
                "     },\n" +
                "     {\n" +
                "      \"code\": \"other\",\n" +
                "      \"amount\": 1570\n" +
                "     }\n" +
                "    ]\n" +
                "   },\n" +
                "   \"validating_carrier\": \"AR\",\n" +
                "   \"last_minute\": false\n" +
                "  }\n" +
                " ],\n" +
                " \"facets\": [\n" +
                "  {\n" +
                "   \"criteria\": \"total_price_range\",\n" +
                "   \"type\": \"range\",\n" +
                "   \"subtype\": \"number\",\n" +
                "   \"min\": \"10821\",\n" +
                "   \"max\": \"38103\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"airlines\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"AC\",\n" +
                "     \"count\": 1,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"LA\",\n" +
                "     \"count\": 73,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"UA\",\n" +
                "     \"count\": 1,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"DL\",\n" +
                "     \"count\": 1,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"AR\",\n" +
                "     \"count\": 37,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"JJ\",\n" +
                "     \"count\": 6,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"AV\",\n" +
                "     \"count\": 1,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"CM\",\n" +
                "     \"count\": 6,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"4M\",\n" +
                "     \"count\": 14,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"AM\",\n" +
                "     \"count\": 1,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"alliances\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"ST\",\n" +
                "     \"count\": 39,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"OW\",\n" +
                "     \"count\": 73,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"SA\",\n" +
                "     \"count\": 15,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"outbound_airports\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"AEP\",\n" +
                "     \"count\": 24,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"EZE\",\n" +
                "     \"count\": 102,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"inbound_airports\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"MIA\",\n" +
                "     \"count\": 126,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"stops\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"none\",\n" +
                "     \"count\": 5,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"one\",\n" +
                "     \"count\": 120,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"more_than_one\",\n" +
                "     \"count\": 1,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"outbound_time\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"morning\",\n" +
                "     \"count\": 26,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"afternoon\",\n" +
                "     \"count\": 40,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"night\",\n" +
                "     \"count\": 37,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"early_morning\",\n" +
                "     \"count\": 23,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"inbound_time\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"text\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"morning\",\n" +
                "     \"count\": 17,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"afternoon\",\n" +
                "     \"count\": 56,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"night\",\n" +
                "     \"count\": 46,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"early_morning\",\n" +
                "     \"count\": 7,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"outbound_duration_range\",\n" +
                "   \"type\": \"range\",\n" +
                "   \"subtype\": \"number\",\n" +
                "   \"min\": \"550\",\n" +
                "   \"max\": \"1640\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"inbound_duration_range\",\n" +
                "   \"type\": \"range\",\n" +
                "   \"subtype\": \"number\",\n" +
                "   \"min\": \"540\",\n" +
                "   \"max\": \"1710\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"outbound_time_range\",\n" +
                "   \"type\": \"range\",\n" +
                "   \"subtype\": \"local_time\",\n" +
                "   \"min\": \"00:50\",\n" +
                "   \"max\": \"23:45\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"inbound_time_range\",\n" +
                "   \"type\": \"range\",\n" +
                "   \"subtype\": \"local_time\",\n" +
                "   \"min\": \"02:00\",\n" +
                "   \"max\": \"21:55\"\n" +
                "  },\n" +
                "  {\n" +
                "   \"criteria\": \"promotions\",\n" +
                "   \"type\": \"discrete\",\n" +
                "   \"subtype\": \"boolean\",\n" +
                "   \"values\": [\n" +
                "    {\n" +
                "     \"value\": \"true\",\n" +
                "     \"count\": 0,\n" +
                "     \"selected\": false\n" +
                "    },\n" +
                "    {\n" +
                "     \"value\": \"false\",\n" +
                "     \"count\": 126,\n" +
                "     \"selected\": false\n" +
                "    }\n" +
                "   ]\n" +
                "  }\n" +
                " ],\n" +
                " \"sorting\": {\n" +
                "  \"criteria\": \"order_by\",\n" +
                "  \"values\": [\n" +
                "   {\n" +
                "    \"value\": \"airline_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"airline_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"arrival_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"arrival_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"commission_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"commission_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"commission_personal_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"commission_personal_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"departure_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"departure_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"duration_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"duration_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"flight_class_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"personal_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"personal_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"price_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"price_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"promotional_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"scoring_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"scoring_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"stopscount_ascending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"stopscount_descending\",\n" +
                "    \"selected\": false\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"total_price_ascending\",\n" +
                "    \"selected\": true\n" +
                "   },\n" +
                "   {\n" +
                "    \"value\": \"total_price_descending\",\n" +
                "    \"selected\": false\n" +
                "   }\n" +
                "  ]\n" +
                " },\n" +
                " \"paging\": {\n" +
                "  \"offset\": 0,\n" +
                "  \"limit\": 1,\n" +
                "  \"total\": 59,\n" +
                "  \"total_itineraries\": 21\n" +
                " }\n" +
                "}";
    }

    @Test
    public void getValidFlights() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(getMockResponse());


        TripsService fs = new TripsService();
        fs.despegarConnector = dc;

        String lf = fs.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10");

        assertTrue(lf.equals(getMockResponse()));
    }

    @Test
    public void getFlightsWrongDates() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("EZE", "MIA", "2015-10-10", "2015-11-10")).thenThrow(new RuntimeException("Error"));

        TripsService fs = new TripsService();
        fs.despegarConnector = dc;

        try {
            fs.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void getFlightsWrongDestiny() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("ZZZ", "MIA", "2015-11-10", "2015-10-10")).thenThrow(new RuntimeException("Error"));


        TripsService fs = new TripsService();
        fs.despegarConnector = dc;

        try {
            fs.getFlights("ZZZ", "MIA", "2015-11-10", "2015-10-10");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void createTrip() {
        TripsRepositoryInterface fr = mock(TripsRepositoryInterface.class);
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fbConn = mock(FacebookConnectorInterface.class);
        AirportsConnectorInterface airportsConnector = mock(AirportsConnectorInterface.class);

        User user = new User();

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("0");

        AirportResponse destiny = new AirportResponse();
        destiny.setCity("Alguna ciudad");

        Trip t = new Trip();

        when(ur.get("0")).thenReturn(user);
        when(ur.update(any(User.class))).thenReturn((user));
        when(fr.insert(any(Trip.class))).thenReturn(t);
        when(fbConn.verifyAccessToken("tokenDePrueba")).thenReturn(fbVerify);
        when(airportsConnector.getAirportAsync(anyString())).thenReturn((destiny));

        TripsService fs = new TripsService();
        fs.tripsRepository = fr;
        fs.userRepository = ur;
        fs.fbConnector = fbConn;
        fs.airportsConnector = airportsConnector;



        assertEquals(fs.createTrip(t, "tokenDePrueba"), t);
    }

    @Test
    public void createFlightFailed() {
        TripsRepositoryInterface tripRepo = mock(TripsRepositoryInterface.class);
        UsersRepositoryInterface userRepo = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fbConn = mock(FacebookConnectorInterface.class);

        User user = new User();

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId("1234");

        when(userRepo.get("1234")).thenReturn((user));
        when(userRepo.update(any(User.class))).thenReturn((user));

        when(tripRepo.insert(any(Trip.class))).thenThrow(new RuntimeException("Missing trip"));

        when(fbConn.verifyAccessToken("tokenDePrueba")).thenReturn(fbVerify);


        TripsService fs = new TripsService();
        fs.tripsRepository = tripRepo;
        fs.userRepository = userRepo;
        fs.fbConnector = fbConn;

        try {
            fs.createTrip(new Trip(), "tokenDePrueba");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }

}
