package com.simona.dao;

import com.simona.model.BaseStation;
import com.simona.model.MobileRadioMonitoringStation;
import com.simona.model.Region;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by alex on 4/4/18.
 */
@Service
public class DaoMock {
    protected static Random random = new Random();

    public List<Region> findAllRegions() {
        List<Region> regions = new LinkedList<>();
        Region region = new Region();
        region.setId(1L);
        region.setRegionName("Kharkov city");
        region.setLatitudeX(50.086873);
        region.setLongitudeX(36.468738);
        region.setLatitudeY(49.891095);
        region.setLongitudeY(36.140521);
        regions.add(region);

        region = new Region();
        region.setId(2L);
        region.setRegionName("Kharkov country");
        region.setLatitudeX(50.380141);
        region.setLongitudeX(37.909320);
        region.setLatitudeY(49.015568);
        region.setLongitudeY(35.189517);
        regions.add(region);

        return regions;
    }

    public List<MobileRadioMonitoringStation> findMobileStationByRegion(Long regionId) {
        List<MobileRadioMonitoringStation> mobileRadioMonitoringStationList = new LinkedList<>();
        if (regionId == 1L) {
            MobileRadioMonitoringStation mobileRadioMonitoringStation = new MobileRadioMonitoringStation();
            mobileRadioMonitoringStation.setId(1L);
            mobileRadioMonitoringStation.setRegionId(1L);
            mobileRadioMonitoringStation.setNameStation("P3/5M №084726");
            mobileRadioMonitoringStation.setLatitudeX(50.058253);
            mobileRadioMonitoringStation.setLongitudeX(36.275790);
            mobileRadioMonitoringStation.setLatitudeY(49.997834);
            mobileRadioMonitoringStation.setLongitudeY(36.142581);
            mobileRadioMonitoringStation.setStatus("online");
            mobileRadioMonitoringStation.setIconName("backCarРМ-1500-Р3:5М.png");
            mobileRadioMonitoringStationList.add(mobileRadioMonitoringStation);

            mobileRadioMonitoringStation = new MobileRadioMonitoringStation();
            mobileRadioMonitoringStation.setId(2L);
            mobileRadioMonitoringStation.setRegionId(1L);
            mobileRadioMonitoringStation.setNameStation("P23M №234467");
            mobileRadioMonitoringStation.setLatitudeX(49.999392);
            mobileRadioMonitoringStation.setLongitudeX(36.280597);
            mobileRadioMonitoringStation.setLatitudeY(49.900878);
            mobileRadioMonitoringStation.setLongitudeY(36.146014);
            mobileRadioMonitoringStation.setStatus("online");
            mobileRadioMonitoringStation.setIconName("blackCarРМ-1500-2Р3.png");
            mobileRadioMonitoringStationList.add(mobileRadioMonitoringStation);

            mobileRadioMonitoringStation = new MobileRadioMonitoringStation();
            mobileRadioMonitoringStation.setId(3L);
            mobileRadioMonitoringStation.setRegionId(1L);
            mobileRadioMonitoringStation.setNameStation("P3/5M №352342");
            mobileRadioMonitoringStation.setLatitudeX(50.086873);
            mobileRadioMonitoringStation.setLongitudeX(36.468738);
            mobileRadioMonitoringStation.setLatitudeY(49.919021);
            mobileRadioMonitoringStation.setLongitudeY(36.283343);
            mobileRadioMonitoringStation.setStatus("online");
            mobileRadioMonitoringStation.setIconName("greenCarРМ-1500-2Р3.png");
            mobileRadioMonitoringStationList.add(mobileRadioMonitoringStation);

        }
        return mobileRadioMonitoringStationList;
    }

    public List<BaseStation> getPointerMRMS(List<Long> regionIds, List<String> mrmsNames) {
        List<BaseStation> baseStationList = new LinkedList<>();
        if (mrmsNames == null) {
            if (regionIds != null) {
                for (Long regionId : regionIds) {
                    if (regionId == 1L) {
                        baseStationList.add(generateRandomPointerMRMS(50.058253, 49.997834, 36.275790, 36.142581));
                        baseStationList.add(generateRandomPointerMRMS(49.999392, 49.900878, 36.280597, 36.146014));
                        baseStationList.add(generateRandomPointerMRMS(50.086873, 49.919021, 36.468738, 36.283343));
                    }
                }
            }
        } else {
            for (String mrmsName : mrmsNames) {
                if ("P3/5M №084726".equals(mrmsName)) {
                    baseStationList.add(generateRandomPointerMRMS(50.058253, 49.997834, 36.275790, 36.142581));
                }
                if ("P23M №234467".equals(mrmsName)) {
                    baseStationList.add(generateRandomPointerMRMS(49.999392, 49.900878, 36.280597, 36.146014));
                }
                if ("P3/5M №352342".equals(mrmsName)) {
                    baseStationList.add(generateRandomPointerMRMS(50.086873, 49.919021, 36.468738, 36.283343));
                }
            }
        }
        return baseStationList;
    }

    public List<BaseStation> getBaseStationsByRegionMRMS(List<Long> regionIds, List<String> mrmsNames) {
        List<BaseStation> baseStationList = new LinkedList<>();
        if (regionIds != null) {
            if (mrmsNames == null) {
                for (Long regionId : regionIds) {
                    if (regionId == 1L) {
                        baseStationList.addAll(generateRandomBaseStation(
                                15000, 50.086873, 49.891095, 36.468738, 36.140521));
                    } else if (regionId == 2L) {
                        baseStationList = generateRandomBaseStation(
                                25000, 50.380141, 49.015568, 37.909320, 35.189517);
                    }
                }
            } else {
                for (String mrmsName : mrmsNames) {
                    if ("P3/5M №084726".equals(mrmsName)) {
//                        baseStationList.addAll(generateRandomBaseStation(
//                                5000, 50.058253, 49.997834, 36.275790, 36.142581));
                        baseStationList.addAll(getFiveSouthanBS084726());
                    }
                    if ("P23M №234467".equals(mrmsName)) {
                        baseStationList.addAll(generateRandomBaseStation(
                                5000, 49.999392, 49.900878, 36.280597, 36.146014));
                    }
                    if ("P3/5M №352342".equals(mrmsName)) {
                        baseStationList.addAll(generateRandomBaseStation(
                                5000, 50.086873, 49.919021, 36.468738, 36.283343));
                    }
                }
            }
        }
        return baseStationList;
    }
    private List<BaseStation> generateRandomBaseStation(Integer count, Double latMax, Double latMin, Double longMax, Double longMin) {
        List<BaseStation> baseStations = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            BaseStation baseStation = new BaseStation();
            baseStation.setId(Long.valueOf(i));
            baseStation.setIconName(generateType());//'yellow''grey''green'
            baseStation.setLatitude(generateLatitude(latMax, latMin));//49.988982 - 49.903915
            baseStation.setLongitude(generateLongitude(longMax, longMin));//36.222230 - 36.090130
            baseStations.add(baseStation);
        }
        return baseStations;
    }
    private BaseStation generateRandomPointerMRMS(Double latMax, Double latMin, Double longMax, Double longMin) {
        BaseStation baseStation = new BaseStation();
        baseStation.setId(Long.valueOf(random.nextInt(50)));
        baseStation.setIconName(generateIconNamePointerMRMS());
        baseStation.setLatitude(generateLatitude(latMax, latMin));//49.988982 - 49.903915
        baseStation.setLongitude(generateLongitude(longMax, longMin));//36.222230 - 36.090130
        return baseStation;
    }

    private static double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    /**
     * max lat - 51.0 ((51 + 90.0) / 180.0 =  0,783333333333333)
     * min lat - 46.0 ((46 + 90.0) / 180.0 = 0,755555555555556)
     * горизонталь
     * (ex.: (49.903915 + 90.0) / 180.0)
     */
    private Double generateLatitude(Double latMax, Double latMin) {
        double min = (latMin + 90.0) / 180.0;
        double max = (latMax + 90.0) / 180.0;
        return (randomInRange(min, max) * 180) - 90.0;
    }

    /**
     * max 40.0 ((40 + 180.0) / 360.0 = 0,611111111111111)
     * min 30.0 ((30 + 180.0) / 360.0 = 0,583333333333333)
     * вертикаль
     * (ex.: (36.090130 + 180.0) / 360.0)
     */
    private Double generateLongitude(Double longMax, Double longMin) {
        double min = (longMin + 180.0) / 360.0;
        double max = (longMax + 180.0) / 360.0;
        return (randomInRange(min, max) * 360) - 180.0;
    }

    private String generateType() {
        int x = random.nextInt(3);
        if (x == 0) {
            return "yellow.png";
        }
        if (x == 1) {
            return "grey.png";
        }
        if (x == 2) {
            return "green.png";
        }
        return "green.png";
    }

    private String generateIconNamePointerMRMS() {
        int x = random.nextInt(8);
        if (x == 0) {
            return "pointer/1.png";
        }
        if (x == 1) {
            return "pointer/2.png";
        }
        if (x == 2) {
            return "pointer/3.png";
        }
        if (x == 3) {
            return "pointer/4.png";
        }
        if (x == 4) {
            return "pointer/5.png";
        }
        if (x == 5) {
            return "pointer/6.png";
        }
        if (x == 6) {
            return "pointer/7.png";
        }
        if (x == 7) {
            return "pointer/8.png";
        }
        return "pointer/8.png";
    }


    private List<BaseStation> getFiveSouthanBS084726() {
        List<BaseStation> baseStationList = new LinkedList<>();

        baseStationList.add(new BaseStation(1000L,50.048979257008796,36.27379808286841,"yellow.png"));
        baseStationList.add(new BaseStation(1001L,50.043936654000674,36.246541242318415,"green.png"));
        baseStationList.add(new BaseStation(1002L,50.01584448147801,36.16867650384421,"grey.png"));
        baseStationList.add(new BaseStation(1003L,50.02707345001838,36.148020683137275,"green.png"));
        baseStationList.add(new BaseStation(1004L,50.03810219746387,36.21398845832633,"yellow.png"));
        baseStationList.add(new BaseStation(1005L,50.04400806038481,36.266465545958994,"grey.png"));
        baseStationList.add(new BaseStation(1006L,50.01117473854626,36.14855984180656,"yellow.png"));
        baseStationList.add(new BaseStation(1007L,50.04880086700766,36.17954518956546,"grey.png"));
        baseStationList.add(new BaseStation(1008L,50.00998764281806,36.245091406269125,"grey.png"));
        baseStationList.add(new BaseStation(1009L,50.05766026858893,36.22345848190636,"yellow.png"));
        baseStationList.add(new BaseStation(1010L,50.019725922826524,36.17899871094443,"grey.png"));
        baseStationList.add(new BaseStation(1011L,50.03141963219136,36.2503915216744,"yellow.png"));
        baseStationList.add(new BaseStation(1012L,50.03985982128876,36.24270773221622,"green.png"));
        baseStationList.add(new BaseStation(1013L,50.03959334720099,36.1434405702785,"grey.png"));
        baseStationList.add(new BaseStation(1014L,50.00656256246896,36.1615077274989,"grey.png"));
        baseStationList.add(new BaseStation(1015L,50.02319987770463,36.224815492556814,"grey.png"));
        baseStationList.add(new BaseStation(1016L,50.00184331167452,36.18586733456206,"yellow.png"));
        baseStationList.add(new BaseStation(1017L,50.01538000856337,36.268746564075514,"yellow.png"));
        baseStationList.add(new BaseStation(1018L,50.00335496967716,36.14951424613648,"green.png"));
        baseStationList.add(new BaseStation(1019L,50.0016645083293,36.19065395708313,"yellow.png"));
        baseStationList.add(new BaseStation(1020L,50.05404816205905,36.18799332540104,"yellow.png"));
        baseStationList.add(new BaseStation(1021L,50.05763637716589,36.25072097463894,"grey.png"));
        baseStationList.add(new BaseStation(1022L,50.01150510158598,36.19484387356002,"grey.png"));
        baseStationList.add(new BaseStation(1023L,50.00647588489878,36.2360979158949,"yellow.png"));
        baseStationList.add(new BaseStation(1024L,50.01784696116445,36.23194430767413,"green.png"));
        baseStationList.add(new BaseStation(1025L,50.03399652961667,36.2295995877067,"yellow.png"));
        baseStationList.add(new BaseStation(1026L,50.035890986881014,36.144345527193025,"grey.png"));
        baseStationList.add(new BaseStation(1027L,50.00505966297692,36.19216370150585,"yellow.png"));
        baseStationList.add(new BaseStation(1028L,50.02493966989206,36.219996040189244,"grey.png"));
        baseStationList.add(new BaseStation(1029L,50.00549289016001,36.26427362670711,"grey.png"));
        baseStationList.add(new BaseStation(1030L,50.03399370280465,36.15033710658241,"yellow.png"));
        baseStationList.add(new BaseStation(1031L,50.051902202692304,36.22587672898791,"grey.png"));
        baseStationList.add(new BaseStation(1032L,50.03423439819966,36.23502634700225,"yellow.png"));
        baseStationList.add(new BaseStation(1033L,50.053516117147836,36.16318973128435,"green.png"));
        baseStationList.add(new BaseStation(1034L,50.006890157273915,36.21206371859171,"green.png"));
        baseStationList.add(new BaseStation(1035L,50.042985376052314,36.155194994357146,"green.png"));
        baseStationList.add(new BaseStation(1036L,50.04105486201516,36.20999315645665,"grey.png"));
        baseStationList.add(new BaseStation(1037L,50.050875131188775,36.171375186159025,"grey.png"));
        baseStationList.add(new BaseStation(1038L,50.03512829091889,36.19115222164385,"grey.png"));
        baseStationList.add(new BaseStation(1039L,50.03733586979948,36.16182200789979,"grey.png"));
        baseStationList.add(new BaseStation(1040L,50.02280298601764,36.24527646030941,"green.png"));
        baseStationList.add(new BaseStation(1041L,50.04883039643889,36.237086725226874,"yellow.png"));
        baseStationList.add(new BaseStation(1042L,50.05002344328216,36.22730412658049,"yellow.png"));
        baseStationList.add(new BaseStation(1043L,50.0386206754433,36.26707812491597,"grey.png"));
        baseStationList.add(new BaseStation(1044L,50.01856570342014,36.268624122340185,"green.png"));
        baseStationList.add(new BaseStation(1045L,50.02232540236065,36.222676139779054,"yellow.png"));
        baseStationList.add(new BaseStation(1046L,50.021096567013416,36.24433268808744,"green.png"));
        baseStationList.add(new BaseStation(1047L,49.9984614403356,36.14904746650856,"grey.png"));
        baseStationList.add(new BaseStation(1048L,50.0241094840988,36.15765441511303,"grey.png"));
        baseStationList.add(new BaseStation(1049L,50.05726312603434,36.195013359025836,"grey.png"));
        baseStationList.add(new BaseStation(1050L,50.0366353148583,36.25722604211293,"grey.png"));
        baseStationList.add(new BaseStation(1051L,50.00998267908298,36.198631734047495,"yellow.png"));
        baseStationList.add(new BaseStation(1052L,50.02732783581328,36.149928542251615,"yellow.png"));
        baseStationList.add(new BaseStation(1053L,50.03954026586632,36.22382895069009,"grey.png"));
        baseStationList.add(new BaseStation(1054L,50.016521199899444,36.20968234134119,"green.png"));
        baseStationList.add(new BaseStation(1055L,50.04810853125514,36.24951534022276,"yellow.png"));
        baseStationList.add(new BaseStation(1056L,49.998794128109864,36.21734810457255,"grey.png"));
        baseStationList.add(new BaseStation(1057L,50.042352582839584,36.23115887179327,"grey.png"));
        baseStationList.add(new BaseStation(1058L,50.02212032595895,36.195465974814766,"grey.png"));
        baseStationList.add(new BaseStation(1059L,50.040695186551744,36.18048328944164,"green.png"));
        baseStationList.add(new BaseStation(1060L,49.99873939520137,36.14878359870298,"yellow.png"));
        baseStationList.add(new BaseStation(1061L,50.05186313388805,36.264417185940005,"green.png"));
        baseStationList.add(new BaseStation(1062L,50.00931855508594,36.22370496381373,"grey.png"));
        baseStationList.add(new BaseStation(1063L,50.03331938574274,36.23608492991343,"yellow.png"));
        baseStationList.add(new BaseStation(1064L,50.03915566289044,36.21721720024493,"green.png"));
        baseStationList.add(new BaseStation(1065L,50.030249665361595,36.227215486274304,"yellow.png"));
        baseStationList.add(new BaseStation(1066L,50.01842338291445,36.226127344445786,"yellow.png"));
        baseStationList.add(new BaseStation(1067L,49.997930238824125,36.233511261034266,"yellow.png"));
        baseStationList.add(new BaseStation(1068L,50.05145114749598,36.210920478472445,"yellow.png"));
        baseStationList.add(new BaseStation(1069L,50.00148239823136,36.236481907851925,"green.png"));
        baseStationList.add(new BaseStation(1070L,50.02075872331554,36.18533583936215,"green.png"));
        baseStationList.add(new BaseStation(1071L,50.05804617331276,36.24591715318829,"yellow.png"));
        baseStationList.add(new BaseStation(1072L,50.04832389039652,36.22031847795708,"green.png"));
        baseStationList.add(new BaseStation(1073L,50.0512459678115,36.26129214887578,"yellow.png"));
        baseStationList.add(new BaseStation(1074L,50.0302958353237,36.21028124201098,"yellow.png"));
        baseStationList.add(new BaseStation(1075L,50.033623307242266,36.216601136307304,"green.png"));
        baseStationList.add(new BaseStation(1076L,50.01894796393765,36.192463573854866,"green.png"));
        baseStationList.add(new BaseStation(1077L,50.0013278060168,36.19485181461857,"grey.png"));
        baseStationList.add(new BaseStation(1078L,50.036498785482195,36.17671050639427,"grey.png"));
        baseStationList.add(new BaseStation(1079L,50.04266193007484,36.25612513957958,"grey.png"));
        baseStationList.add(new BaseStation(1080L,50.04089282569649,36.18798925935357,"green.png"));
        baseStationList.add(new BaseStation(1081L,50.03499568096703,36.25061718592008,"yellow.png"));
        baseStationList.add(new BaseStation(1082L,50.022547466778235,36.18115791816504,"green.png"));
        baseStationList.add(new BaseStation(1083L,50.02861095587153,36.240236449390466,"yellow.png"));
        baseStationList.add(new BaseStation(1084L,50.01886234846944,36.170770313246095,"grey.png"));
        baseStationList.add(new BaseStation(1085L,50.04752320881056,36.17315630873529,"grey.png"));
        baseStationList.add(new BaseStation(1086L,50.024616643899634,36.14820525666147,"yellow.png"));
        baseStationList.add(new BaseStation(1087L,50.05436097179188,36.21042053770907,"green.png"));
        baseStationList.add(new BaseStation(1088L,50.05305709799538,36.23639658033207,"green.png"));
        baseStationList.add(new BaseStation(1089L,50.010552209835794,36.25540077588798,"grey.png"));
        baseStationList.add(new BaseStation(1090L,49.999390975145815,36.20623437652256,"yellow.png"));
        baseStationList.add(new BaseStation(1091L,50.05436119646089,36.23174643942065,"yellow.png"));
        baseStationList.add(new BaseStation(1092L,50.01706270398799,36.206452209863414,"yellow.png"));
        baseStationList.add(new BaseStation(1093L,50.04331122354836,36.213363155779405,"green.png"));
        baseStationList.add(new BaseStation(1094L,50.03378819483888,36.27126115827434,"green.png"));
        baseStationList.add(new BaseStation(1095L,50.05366222046155,36.15814665096872,"green.png"));
        baseStationList.add(new BaseStation(1096L,50.00576847063337,36.19795437556638,"green.png"));
        baseStationList.add(new BaseStation(1097L,50.02637695329972,36.16356203120148,"green.png"));
        baseStationList.add(new BaseStation(1098L,50.016644636435586,36.178132508472544,"grey.png"));
        baseStationList.add(new BaseStation(1099L,50.012063393417236,36.22618472702203,"green.png"));
        baseStationList.add(new BaseStation(1100L,50.052318417815144,36.25797731936754,"yellow.png"));
        baseStationList.add(new BaseStation(1101L,50.032988734614804,36.16793145835666,"grey.png"));
        baseStationList.add(new BaseStation(1102L,50.012916086095515,36.173422645503564,"yellow.png"));
        baseStationList.add(new BaseStation(1103L,50.045412247824345,36.25114297626567,"grey.png"));
        baseStationList.add(new BaseStation(1104L,50.02851653074623,36.1826777407822,"yellow.png"));
        baseStationList.add(new BaseStation(1105L,50.031105649116256,36.184045004131406,"grey.png"));
        baseStationList.add(new BaseStation(1106L,50.04225853612169,36.239468020944,"grey.png"));
        baseStationList.add(new BaseStation(1107L,50.029185397379194,36.23760667971726,"green.png"));
        baseStationList.add(new BaseStation(1108L,50.02205349760857,36.25780163750218,"grey.png"));
        baseStationList.add(new BaseStation(1109L,50.00776053027772,36.15340242190891,"grey.png"));
        baseStationList.add(new BaseStation(1110L,50.03320899775156,36.24415516735439,"grey.png"));
        baseStationList.add(new BaseStation(1111L,50.032968887115544,36.14949897751043,"green.png"));
        baseStationList.add(new BaseStation(1112L,49.99900960865608,36.26352746539271,"green.png"));
        baseStationList.add(new BaseStation(1113L,50.00547920465496,36.21096920630902,"grey.png"));
        baseStationList.add(new BaseStation(1114L,50.009522082138034,36.26311448105196,"grey.png"));
        baseStationList.add(new BaseStation(1115L,50.05282062165418,36.19641275884035,"green.png"));
        baseStationList.add(new BaseStation(1116L,50.04513255212035,36.20352312898865,"grey.png"));
        baseStationList.add(new BaseStation(1117L,50.02142567183287,36.244850768975766,"grey.png"));
        baseStationList.add(new BaseStation(1118L,50.03727206027531,36.15712258105842,"yellow.png"));
        baseStationList.add(new BaseStation(1119L,50.056045069555665,36.17748922792032,"yellow.png"));
        baseStationList.add(new BaseStation(1120L,50.03137877235821,36.17037242422856,"yellow.png"));
        baseStationList.add(new BaseStation(1121L,50.03029879438779,36.26920192106775,"yellow.png"));
        baseStationList.add(new BaseStation(1122L,50.02243436033186,36.22451872319087,"green.png"));
        baseStationList.add(new BaseStation(1123L,50.00480806557718,36.15860821921325,"yellow.png"));
        baseStationList.add(new BaseStation(1124L,50.04883228948265,36.23267897345079,"yellow.png"));
        baseStationList.add(new BaseStation(1125L,50.050613514114474,36.227837769649426,"grey.png"));
        baseStationList.add(new BaseStation(1126L,50.00826385272089,36.26523756063264,"yellow.png"));
        baseStationList.add(new BaseStation(1127L,50.01877102439505,36.190609061444604,"grey.png"));
        baseStationList.add(new BaseStation(1128L,50.0541964639375,36.17756817368564,"grey.png"));
        baseStationList.add(new BaseStation(1129L,50.043767474116436,36.19543933117956,"green.png"));
        baseStationList.add(new BaseStation(1130L,50.017845410831825,36.161495003714464,"green.png"));
        baseStationList.add(new BaseStation(1131L,50.04071275893216,36.26759490569921,"grey.png"));
        baseStationList.add(new BaseStation(1132L,50.04117843506427,36.23626033440942,"green.png"));
        baseStationList.add(new BaseStation(1133L,50.00624150812891,36.17468348000233,"grey.png"));
        baseStationList.add(new BaseStation(1134L,50.02035130510433,36.226619547932074,"yellow.png"));
        baseStationList.add(new BaseStation(1135L,50.00584122001092,36.27485205414044,"yellow.png"));
        baseStationList.add(new BaseStation(1136L,50.05386686374496,36.21218192287603,"grey.png"));
        baseStationList.add(new BaseStation(1137L,50.02580841053265,36.1662033688165,"green.png"));
        baseStationList.add(new BaseStation(1138L,50.0433795574985,36.17531019568656,"yellow.png"));
        baseStationList.add(new BaseStation(1139L,50.04604082487623,36.243757883293,"green.png"));
        baseStationList.add(new BaseStation(1140L,50.013766014485014,36.15477042397782,"green.png"));
        baseStationList.add(new BaseStation(1141L,50.04567321674011,36.151802242744054,"yellow.png"));
        baseStationList.add(new BaseStation(1142L,50.01432787199204,36.20478432943841,"green.png"));
        baseStationList.add(new BaseStation(1143L,50.0514013519832,36.26137144541238,"grey.png"));
        baseStationList.add(new BaseStation(1144L,50.028722398185124,36.246577448483436,"yellow.png"));
        baseStationList.add(new BaseStation(1145L,50.0446961213909,36.26872930313098,"yellow.png"));
        baseStationList.add(new BaseStation(1146L,50.027741620422006,36.2583556240821,"grey.png"));
        baseStationList.add(new BaseStation(1147L,50.01900726779746,36.15768274150244,"yellow.png"));
        baseStationList.add(new BaseStation(1148L,49.99939440143439,36.23013191232505,"grey.png"));
        baseStationList.add(new BaseStation(1149L,50.029969093381226,36.22064345194579,"green.png"));
        baseStationList.add(new BaseStation(1150L,50.01013439587888,36.228439832982446,"grey.png"));
        baseStationList.add(new BaseStation(1151L,50.03033834726236,36.177710576369265,"yellow.png"));
        baseStationList.add(new BaseStation(1152L,50.05274707046371,36.223385941173035,"green.png"));
        baseStationList.add(new BaseStation(1153L,50.00025600317463,36.21022631644627,"green.png"));
        baseStationList.add(new BaseStation(1154L,50.019862066270264,36.23492605868958,"green.png"));
        baseStationList.add(new BaseStation(1155L,50.0315347430373,36.18321726062857,"green.png"));
        baseStationList.add(new BaseStation(1156L,50.018504258564235,36.218900749768466,"green.png"));
        baseStationList.add(new BaseStation(1157L,50.055354803393755,36.20898330418572,"yellow.png"));
        baseStationList.add(new BaseStation(1158L,50.04186228221215,36.226903133232184,"grey.png"));
        baseStationList.add(new BaseStation(1159L,50.04715274338352,36.25705906991544,"grey.png"));
        baseStationList.add(new BaseStation(1160L,50.04334229524076,36.2092546522515,"green.png"));
        baseStationList.add(new BaseStation(1161L,50.031274646573905,36.15080964893443,"grey.png"));
        baseStationList.add(new BaseStation(1162L,50.01763605802492,36.17408474504177,"grey.png"));
        baseStationList.add(new BaseStation(1163L,49.99807292951266,36.247673042285584,"green.png"));
        baseStationList.add(new BaseStation(1164L,50.05322259745023,36.171618351591974,"yellow.png"));
        baseStationList.add(new BaseStation(1165L,50.043390981817,36.228044616930305,"yellow.png"));
        baseStationList.add(new BaseStation(1166L,50.03288788820612,36.234584359862225,"green.png"));
        baseStationList.add(new BaseStation(1167L,50.00780785875952,36.17718325517248,"green.png"));
        baseStationList.add(new BaseStation(1168L,50.03386585500925,36.26304622032305,"grey.png"));
        baseStationList.add(new BaseStation(1169L,50.01203219082569,36.25911046072338,"grey.png"));
        baseStationList.add(new BaseStation(1170L,50.00645276791036,36.21161332178241,"green.png"));
        baseStationList.add(new BaseStation(1171L,50.01716169587306,36.20832032231098,"yellow.png"));
        baseStationList.add(new BaseStation(1172L,50.03565365494151,36.19487759125249,"green.png"));
        baseStationList.add(new BaseStation(1173L,50.02171239860107,36.26768568617979,"green.png"));
        baseStationList.add(new BaseStation(1174L,50.03771639749428,36.203476660074784,"grey.png"));
        baseStationList.add(new BaseStation(1175L,50.01676406005498,36.2698197670567,"grey.png"));
        baseStationList.add(new BaseStation(1176L,50.01445131960895,36.229136052270746,"yellow.png"));
        baseStationList.add(new BaseStation(1177L,50.011058213590985,36.18426130609407,"green.png"));
        baseStationList.add(new BaseStation(1178L,50.00290940418293,36.197523966563466,"green.png"));
        baseStationList.add(new BaseStation(1179L,50.012523081747275,36.18435098700098,"yellow.png"));
        baseStationList.add(new BaseStation(1180L,50.03418204441559,36.220378611099505,"yellow.png"));
        baseStationList.add(new BaseStation(1181L,50.009824633224014,36.274161980326994,"grey.png"));
        baseStationList.add(new BaseStation(1182L,50.03787493559133,36.177640089691835,"grey.png"));
        baseStationList.add(new BaseStation(1183L,50.02981786290496,36.27418280671009,"yellow.png"));
        baseStationList.add(new BaseStation(1184L,50.00742985442574,36.1957786445366,"grey.png"));
        baseStationList.add(new BaseStation(1185L,50.03423928641348,36.22295587404383,"yellow.png"));
        baseStationList.add(new BaseStation(1186L,50.01268502895897,36.23669606833903,"green.png"));
        baseStationList.add(new BaseStation(1187L,50.0391370818281,36.16055217256718,"yellow.png"));
        baseStationList.add(new BaseStation(1188L,50.03876277795297,36.15442540031353,"green.png"));
        baseStationList.add(new BaseStation(1189L,50.03646535379187,36.246005562835876,"grey.png"));
        baseStationList.add(new BaseStation(1190L,50.02111820866091,36.27200083188362,"yellow.png"));
        baseStationList.add(new BaseStation(1191L,50.0180129698324,36.180199944163235,"grey.png"));
        baseStationList.add(new BaseStation(1192L,50.00504777130598,36.16121575562423,"yellow.png"));
        baseStationList.add(new BaseStation(1193L,50.05603053059056,36.205366738601015,"green.png"));
        baseStationList.add(new BaseStation(1194L,50.01764645453247,36.21550230454778,"grey.png"));
        baseStationList.add(new BaseStation(1195L,50.026518513776296,36.217598480832464,"yellow.png"));
        baseStationList.add(new BaseStation(1196L,50.00441024674126,36.267861282847036,"green.png"));
        baseStationList.add(new BaseStation(1197L,50.049946311144964,36.266673270913145,"yellow.png"));
        baseStationList.add(new BaseStation(1198L,50.01900464264824,36.22209394811671,"green.png"));
        baseStationList.add(new BaseStation(1199L,50.004191218385785,36.18195352154953,"grey.png"));
        baseStationList.add(new BaseStation(1200L,50.0068504661416,36.252401561409215,"green.png"));
        baseStationList.add(new BaseStation(1201L,50.04593408011573,36.14343297123608,"yellow.png"));
        baseStationList.add(new BaseStation(1202L,50.043358374393506,36.197111052329376,"yellow.png"));
        baseStationList.add(new BaseStation(1203L,50.056658666830174,36.274431017755035,"grey.png"));
        baseStationList.add(new BaseStation(1204L,50.002873047088,36.24468798269663,"green.png"));
        baseStationList.add(new BaseStation(1205L,50.026250181882034,36.23039624343241,"yellow.png"));
        baseStationList.add(new BaseStation(1206L,50.05480172819787,36.2316574578424,"yellow.png"));
        baseStationList.add(new BaseStation(1207L,50.03240517940296,36.180619020757064,"green.png"));
        baseStationList.add(new BaseStation(1208L,50.05406946913109,36.252620353122325,"green.png"));
        baseStationList.add(new BaseStation(1209L,50.041571484487065,36.23909282280417,"grey.png"));
        baseStationList.add(new BaseStation(1210L,50.02427227556075,36.26875765108477,"yellow.png"));
        baseStationList.add(new BaseStation(1211L,50.03204312233345,36.242066869550825,"grey.png"));
        baseStationList.add(new BaseStation(1212L,50.053810711294545,36.26405458383638,"green.png"));
        baseStationList.add(new BaseStation(1213L,50.01470327428805,36.17504657356861,"yellow.png"));
        baseStationList.add(new BaseStation(1214L,50.051211110954824,36.182280386278904,"yellow.png"));
        baseStationList.add(new BaseStation(1215L,50.037956502897174,36.18484879065363,"yellow.png"));
        baseStationList.add(new BaseStation(1216L,50.01656533933917,36.271218192118056,"green.png"));
        baseStationList.add(new BaseStation(1217L,50.00867261877025,36.25001016060551,"grey.png"));
        baseStationList.add(new BaseStation(1218L,50.030029692617006,36.15768518915036,"green.png"));
        baseStationList.add(new BaseStation(1219L,50.02391302821832,36.25925171662803,"yellow.png"));
        baseStationList.add(new BaseStation(1220L,50.00244290760193,36.19567892776237,"grey.png"));
        baseStationList.add(new BaseStation(1221L,50.00035109460384,36.2169249437695,"yellow.png"));
        baseStationList.add(new BaseStation(1222L,50.02808093385565,36.16757808660799,"grey.png"));
        baseStationList.add(new BaseStation(1223L,50.031473273439474,36.156168536054565,"yellow.png"));
        baseStationList.add(new BaseStation(1224L,50.03188687979949,36.209887931904916,"green.png"));
        baseStationList.add(new BaseStation(1225L,50.01428169778495,36.264805947370434,"green.png"));
        baseStationList.add(new BaseStation(1226L,49.99865200369234,36.23709710886581,"grey.png"));
        baseStationList.add(new BaseStation(1227L,50.03546121210249,36.204900438864286,"yellow.png"));
        baseStationList.add(new BaseStation(1228L,50.04930853217206,36.21808407867664,"green.png"));
        baseStationList.add(new BaseStation(1229L,50.00363359951095,36.21477961040051,"green.png"));
        baseStationList.add(new BaseStation(1230L,50.05487692891663,36.193757034965984,"grey.png"));
        baseStationList.add(new BaseStation(1231L,50.00036845411498,36.26253786806919,"yellow.png"));
        baseStationList.add(new BaseStation(1232L,50.03186786981277,36.2305174005441,"green.png"));
        baseStationList.add(new BaseStation(1233L,50.04107250844814,36.254725880442805,"green.png"));
        baseStationList.add(new BaseStation(1234L,49.99944913193343,36.158516172668925,"green.png"));
        baseStationList.add(new BaseStation(1235L,50.03598686539755,36.224819438019495,"yellow.png"));
        baseStationList.add(new BaseStation(1236L,50.044367289001684,36.214824389212765,"green.png"));
        baseStationList.add(new BaseStation(1237L,50.02943987360993,36.143867284823614,"green.png"));
        baseStationList.add(new BaseStation(1238L,50.00923191756701,36.19745209220025,"grey.png"));
        baseStationList.add(new BaseStation(1239L,50.02995108881049,36.14410010459423,"yellow.png"));
        baseStationList.add(new BaseStation(1240L,50.02442192549026,36.259154172428,"grey.png"));
        baseStationList.add(new BaseStation(1241L,49.99845689362863,36.19592321037581,"grey.png"));
        baseStationList.add(new BaseStation(1242L,50.03040233327974,36.186220001603715,"green.png"));
        baseStationList.add(new BaseStation(1243L,50.00528406064936,36.24613946032733,"yellow.png"));
        baseStationList.add(new BaseStation(1244L,50.038820920907455,36.143759229713794,"green.png"));
        baseStationList.add(new BaseStation(1245L,50.03797272225728,36.20448951720863,"green.png"));
        baseStationList.add(new BaseStation(1246L,50.02300556941191,36.21589006244358,"green.png"));
        baseStationList.add(new BaseStation(1247L,50.0042241950043,36.25049308880466,"grey.png"));
        baseStationList.add(new BaseStation(1248L,50.00552850237935,36.18707317418597,"yellow.png"));
        baseStationList.add(new BaseStation(1249L,50.031671008900275,36.24424093102968,"green.png"));
        baseStationList.add(new BaseStation(1250L,50.05435563710637,36.20388167535478,"green.png"));
        baseStationList.add(new BaseStation(1251L,50.02497315727916,36.235945301840644,"yellow.png"));
        baseStationList.add(new BaseStation(1252L,50.01394711363454,36.15952101030936,"grey.png"));
        baseStationList.add(new BaseStation(1253L,50.04524554489578,36.25154358316587,"green.png"));
        baseStationList.add(new BaseStation(1254L,50.006241593872716,36.198532318653804,"grey.png"));
        baseStationList.add(new BaseStation(1255L,49.99861330611441,36.263607570677976,"grey.png"));
        baseStationList.add(new BaseStation(1256L,50.011315180495444,36.26593768064117,"grey.png"));
        baseStationList.add(new BaseStation(1257L,49.99951772595486,36.184333694222175,"green.png"));
        baseStationList.add(new BaseStation(1258L,50.05159149560001,36.14454069617989,"green.png"));
        baseStationList.add(new BaseStation(1259L,50.05043510972072,36.17061469661979,"green.png"));
        baseStationList.add(new BaseStation(1260L,50.033827323155094,36.2544721815442,"green.png"));
        baseStationList.add(new BaseStation(1261L,50.01314253595328,36.14654319483694,"green.png"));
        baseStationList.add(new BaseStation(1262L,50.010895938028085,36.23942119424228,"grey.png"));
        baseStationList.add(new BaseStation(1263L,50.054504305774344,36.244268122039074,"green.png"));
        baseStationList.add(new BaseStation(1264L,50.05528642844126,36.1664875988742,"green.png"));
        baseStationList.add(new BaseStation(1265L,49.99970795501781,36.23284930236957,"grey.png"));
        baseStationList.add(new BaseStation(1266L,49.998827756399834,36.226732862695314,"green.png"));
        baseStationList.add(new BaseStation(1267L,50.00814192380295,36.2425110722194,"grey.png"));
        baseStationList.add(new BaseStation(1268L,50.005930365231876,36.17275697408121,"green.png"));
        baseStationList.add(new BaseStation(1269L,50.049531054705085,36.24009514885432,"grey.png"));
        baseStationList.add(new BaseStation(1270L,50.044332073172484,36.27318474388218,"green.png"));
        baseStationList.add(new BaseStation(1271L,50.008026698211864,36.27252802632333,"green.png"));
        baseStationList.add(new BaseStation(1272L,50.03542889717582,36.20780480381637,"green.png"));
        baseStationList.add(new BaseStation(1273L,50.011101487252034,36.19484517462331,"grey.png"));
        baseStationList.add(new BaseStation(1274L,50.030489265879794,36.24299455326104,"green.png"));
        baseStationList.add(new BaseStation(1275L,50.0258659148661,36.14800060890198,"green.png"));
        baseStationList.add(new BaseStation(1276L,50.02419349420924,36.146646391612734,"yellow.png"));
        baseStationList.add(new BaseStation(1277L,50.04320064659893,36.25215964286508,"grey.png"));
        baseStationList.add(new BaseStation(1278L,50.04904303521329,36.175492518308204,"grey.png"));
        baseStationList.add(new BaseStation(1279L,50.028584538810236,36.22196198112832,"green.png"));
        baseStationList.add(new BaseStation(1280L,50.052595994920125,36.18489719702424,"yellow.png"));
        baseStationList.add(new BaseStation(1281L,50.05138827626706,36.26895188882119,"yellow.png"));
        baseStationList.add(new BaseStation(1282L,50.02638416544701,36.24575718571825,"grey.png"));
        baseStationList.add(new BaseStation(1283L,50.032161076552256,36.167077228056826,"grey.png"));
        baseStationList.add(new BaseStation(1284L,49.99863296848059,36.27215943532076,"grey.png"));
        baseStationList.add(new BaseStation(1285L,50.0066409268166,36.25830704112664,"grey.png"));
        baseStationList.add(new BaseStation(1286L,50.03371856434788,36.21346002455488,"yellow.png"));
        baseStationList.add(new BaseStation(1287L,50.02286021035047,36.2729785801333,"yellow.png"));
        baseStationList.add(new BaseStation(1288L,50.02183206965998,36.26455456396059,"yellow.png"));
        baseStationList.add(new BaseStation(1289L,50.02888382964548,36.22597580092756,"yellow.png"));
        baseStationList.add(new BaseStation(1290L,50.03207729009631,36.18597762324748,"yellow.png"));
        baseStationList.add(new BaseStation(1291L,50.01298271755968,36.175099695930044,"grey.png"));
        baseStationList.add(new BaseStation(1292L,50.00646669645732,36.2733410028365,"green.png"));
        baseStationList.add(new BaseStation(1293L,50.05613310650372,36.26372585816176,"yellow.png"));
        baseStationList.add(new BaseStation(1294L,50.03425549540998,36.16762450897505,"yellow.png"));
        baseStationList.add(new BaseStation(1295L,50.019407750147224,36.1571188516933,"green.png"));
        baseStationList.add(new BaseStation(1296L,50.024038906309244,36.21523786479054,"grey.png"));
        baseStationList.add(new BaseStation(1297L,50.00266365599157,36.223410811704554,"green.png"));
        baseStationList.add(new BaseStation(1298L,50.0373170622039,36.16604369174678,"green.png"));
        baseStationList.add(new BaseStation(1299L,49.99958061800834,36.231729012580956,"yellow.png"));
        baseStationList.add(new BaseStation(1300L,50.019336507030175,36.14598584761774,"yellow.png"));
        baseStationList.add(new BaseStation(1301L,50.038206607929254,36.26264507997911,"yellow.png"));
        baseStationList.add(new BaseStation(1302L,50.003705396318,36.25473692114426,"green.png"));
        baseStationList.add(new BaseStation(1303L,50.050304816507804,36.25818704475944,"green.png"));
        baseStationList.add(new BaseStation(1304L,50.05083148044295,36.1559564443713,"yellow.png"));
        baseStationList.add(new BaseStation(1305L,50.02888441496404,36.188950501794324,"yellow.png"));
        baseStationList.add(new BaseStation(1306L,50.045486776229495,36.25188893903206,"green.png"));
        baseStationList.add(new BaseStation(1307L,50.05607925962488,36.26362968679567,"yellow.png"));
        baseStationList.add(new BaseStation(1308L,50.00322087267028,36.251125049112744,"yellow.png"));
        baseStationList.add(new BaseStation(1309L,50.05157673915758,36.2249231928532,"yellow.png"));
        baseStationList.add(new BaseStation(1310L,50.03159943723415,36.236518906069904,"grey.png"));
        baseStationList.add(new BaseStation(1311L,50.00056834966901,36.19589896620403,"yellow.png"));
        baseStationList.add(new BaseStation(1312L,50.03264403651289,36.21098382649177,"grey.png"));
        baseStationList.add(new BaseStation(1313L,50.023476582186106,36.153221220214306,"yellow.png"));
        baseStationList.add(new BaseStation(1314L,50.00036635367459,36.17298531622001,"green.png"));
        baseStationList.add(new BaseStation(1315L,50.001831173105614,36.17385824648264,"grey.png"));
        baseStationList.add(new BaseStation(1316L,50.03085653235863,36.17646092535662,"green.png"));
        baseStationList.add(new BaseStation(1317L,50.043225972491456,36.18017306495122,"grey.png"));
        baseStationList.add(new BaseStation(1318L,50.02553052442525,36.24663887859751,"grey.png"));
        baseStationList.add(new BaseStation(1319L,50.03204081979507,36.22234689562691,"green.png"));
        baseStationList.add(new BaseStation(1320L,50.03172841986492,36.187765184110134,"grey.png"));
        baseStationList.add(new BaseStation(1321L,50.003953136260066,36.22178124777716,"grey.png"));
        baseStationList.add(new BaseStation(1322L,50.037406345764396,36.193850469830494,"green.png"));
        baseStationList.add(new BaseStation(1323L,50.028255227996226,36.15307154774035,"green.png"));
        baseStationList.add(new BaseStation(1324L,50.005716466781564,36.1658209362991,"grey.png"));
        baseStationList.add(new BaseStation(1325L,50.030901874234985,36.22024824331007,"grey.png"));
        baseStationList.add(new BaseStation(1326L,50.04524589902027,36.19899540543648,"green.png"));
        baseStationList.add(new BaseStation(1327L,50.02286931883742,36.239240065501605,"grey.png"));
        baseStationList.add(new BaseStation(1328L,50.03403143087078,36.20036930494709,"grey.png"));
        baseStationList.add(new BaseStation(1329L,50.04837484108779,36.16910479270376,"yellow.png"));
        baseStationList.add(new BaseStation(1330L,50.042969308022265,36.171383864936644,"grey.png"));
        baseStationList.add(new BaseStation(1331L,50.04594160858187,36.16336007866411,"grey.png"));
        baseStationList.add(new BaseStation(1332L,50.05307897334882,36.21505725889048,"grey.png"));
        baseStationList.add(new BaseStation(1333L,50.05692219234106,36.14530244263037,"yellow.png"));
        baseStationList.add(new BaseStation(1334L,50.00733963327016,36.263923322331465,"green.png"));
        baseStationList.add(new BaseStation(1335L,50.03225605550173,36.19086718150004,"yellow.png"));
        baseStationList.add(new BaseStation(1336L,50.03500754568418,36.23921582488475,"grey.png"));
        baseStationList.add(new BaseStation(1337L,50.04640032688755,36.25047740398966,"grey.png"));
        baseStationList.add(new BaseStation(1338L,50.04099858606759,36.17060414413331,"grey.png"));
        baseStationList.add(new BaseStation(1339L,50.0320524158594,36.17882748620261,"grey.png"));
        baseStationList.add(new BaseStation(1340L,50.02892024740859,36.15288075677259,"grey.png"));
        baseStationList.add(new BaseStation(1341L,50.01432809707029,36.23545766768356,"green.png"));
        baseStationList.add(new BaseStation(1342L,50.02313581765506,36.169628244588694,"yellow.png"));
        baseStationList.add(new BaseStation(1343L,50.05235162919598,36.168957234228685,"green.png"));
        baseStationList.add(new BaseStation(1344L,50.03614481818613,36.23098585487577,"yellow.png"));
        baseStationList.add(new BaseStation(1345L,50.0032582218449,36.234230357304995,"green.png"));
        baseStationList.add(new BaseStation(1346L,50.05696707429567,36.2184596783365,"yellow.png"));
        baseStationList.add(new BaseStation(1347L,50.0106932242353,36.272620880779556,"grey.png"));
        baseStationList.add(new BaseStation(1348L,50.02124391745542,36.260532839688466,"yellow.png"));
        baseStationList.add(new BaseStation(1349L,50.0184559429521,36.24683792878193,"green.png"));
        baseStationList.add(new BaseStation(1350L,50.04312766632941,36.25177642813338,"yellow.png"));
        baseStationList.add(new BaseStation(1351L,50.02052414886765,36.227735260399356,"yellow.png"));
        baseStationList.add(new BaseStation(1352L,50.01817107677553,36.151790612513565,"green.png"));
        baseStationList.add(new BaseStation(1353L,50.05690609015733,36.17370575775985,"yellow.png"));
        baseStationList.add(new BaseStation(1354L,50.044549590883804,36.1447654633634,"green.png"));
        baseStationList.add(new BaseStation(1355L,50.002994514764,36.1637164492692,"yellow.png"));
        baseStationList.add(new BaseStation(1356L,49.99968328911186,36.221911369237944,"yellow.png"));
        baseStationList.add(new BaseStation(1357L,50.03107934195634,36.25809654031687,"yellow.png"));
        baseStationList.add(new BaseStation(1358L,50.02961527595221,36.25523418245277,"yellow.png"));
        baseStationList.add(new BaseStation(1359L,50.024956584795405,36.25831408808472,"green.png"));
        baseStationList.add(new BaseStation(1360L,50.000156707303574,36.24436854311338,"grey.png"));
        baseStationList.add(new BaseStation(1361L,50.013011960651085,36.22136027355751,"yellow.png"));
        baseStationList.add(new BaseStation(1362L,50.011391261841965,36.202918200081,"grey.png"));
        baseStationList.add(new BaseStation(1363L,50.018423784637434,36.179499988379774,"green.png"));
        baseStationList.add(new BaseStation(1364L,50.04582176926232,36.16376396305344,"yellow.png"));
        baseStationList.add(new BaseStation(1365L,50.04942966380838,36.187547791882906,"grey.png"));
        baseStationList.add(new BaseStation(1366L,50.018763426838234,36.196309446507826,"green.png"));
        baseStationList.add(new BaseStation(1367L,50.01351278751429,36.243594536486114,"yellow.png"));
        baseStationList.add(new BaseStation(1368L,50.051877243603826,36.2168275639595,"green.png"));
        baseStationList.add(new BaseStation(1369L,50.0041666697262,36.21559601498987,"grey.png"));
        baseStationList.add(new BaseStation(1370L,50.002951544431085,36.2133206252073,"green.png"));
        baseStationList.add(new BaseStation(1371L,50.03901412239867,36.16100477686234,"yellow.png"));
        baseStationList.add(new BaseStation(1372L,49.99942699770696,36.196822358463635,"grey.png"));
        baseStationList.add(new BaseStation(1373L,50.04410843135261,36.23773608597503,"green.png"));
        baseStationList.add(new BaseStation(1374L,50.051946522857406,36.217009455919026,"yellow.png"));
        baseStationList.add(new BaseStation(1375L,50.04347744204111,36.275228616747,"green.png"));
        baseStationList.add(new BaseStation(1376L,50.02277689570897,36.259904405924004,"grey.png"));
        baseStationList.add(new BaseStation(1377L,50.01156218945411,36.189396536688406,"grey.png"));
        baseStationList.add(new BaseStation(1378L,50.00397447398322,36.25254390879397,"grey.png"));
        baseStationList.add(new BaseStation(1379L,50.03416235785747,36.150765047441695,"grey.png"));
        baseStationList.add(new BaseStation(1380L,50.001780542455066,36.17902931383895,"green.png"));
        baseStationList.add(new BaseStation(1381L,50.003886330745814,36.22425282480663,"green.png"));
        baseStationList.add(new BaseStation(1382L,50.03301056271471,36.2130108013462,"green.png"));
        baseStationList.add(new BaseStation(1383L,50.02537867907759,36.26069553073293,"green.png"));
        baseStationList.add(new BaseStation(1384L,50.05348174937404,36.22642476141516,"green.png"));
        baseStationList.add(new BaseStation(1385L,50.04352750049034,36.225435803481076,"green.png"));
        baseStationList.add(new BaseStation(1386L,50.00812812958961,36.23907720897017,"grey.png"));
        baseStationList.add(new BaseStation(1387L,50.03332435406415,36.25002201378132,"grey.png"));
        baseStationList.add(new BaseStation(1388L,50.0081548062642,36.20920922820963,"grey.png"));
        baseStationList.add(new BaseStation(1389L,50.027701882703155,36.22489392306187,"green.png"));
        baseStationList.add(new BaseStation(1390L,50.00924696967846,36.17483368536563,"green.png"));
        baseStationList.add(new BaseStation(1391L,50.053371316155335,36.215476187172925,"grey.png"));
        baseStationList.add(new BaseStation(1392L,50.0260819660468,36.21502691380843,"yellow.png"));
        baseStationList.add(new BaseStation(1393L,50.0035634459864,36.207557780952726,"grey.png"));
        baseStationList.add(new BaseStation(1394L,50.00441847767314,36.18907834217174,"grey.png"));
        baseStationList.add(new BaseStation(1395L,50.05647872659583,36.25726405297493,"yellow.png"));
        baseStationList.add(new BaseStation(1396L,50.048583807664954,36.218433205739075,"grey.png"));
        baseStationList.add(new BaseStation(1397L,50.02614095373218,36.26062422235307,"green.png"));
        baseStationList.add(new BaseStation(1398L,50.00435607505574,36.24562855437543,"grey.png"));
        baseStationList.add(new BaseStation(1399L,50.016838631015986,36.22567300367439,"yellow.png"));
        baseStationList.add(new BaseStation(1400L,50.04401262714421,36.24771170725376,"green.png"));
        baseStationList.add(new BaseStation(1401L,49.99921435338774,36.19973550373794,"grey.png"));
        baseStationList.add(new BaseStation(1402L,50.00500831521097,36.24888778535225,"grey.png"));
        baseStationList.add(new BaseStation(1403L,50.043090186199436,36.21535703795203,"green.png"));
        baseStationList.add(new BaseStation(1404L,50.003443938465125,36.21775149065732,"yellow.png"));
        baseStationList.add(new BaseStation(1405L,50.00844759800492,36.241658193504634,"green.png"));
        baseStationList.add(new BaseStation(1406L,50.00374216527922,36.264544834700985,"grey.png"));
        baseStationList.add(new BaseStation(1407L,50.04083922640399,36.174985488296386,"grey.png"));
        baseStationList.add(new BaseStation(1408L,50.008658081371635,36.17405234530602,"yellow.png"));
        baseStationList.add(new BaseStation(1409L,50.006529988169746,36.15439256527054,"green.png"));
        baseStationList.add(new BaseStation(1410L,50.04300542396919,36.24550219912197,"grey.png"));
        baseStationList.add(new BaseStation(1411L,50.044032872044426,36.26698846056121,"grey.png"));
        baseStationList.add(new BaseStation(1412L,50.01180455806073,36.2285642428582,"green.png"));
        baseStationList.add(new BaseStation(1413L,50.001071778182705,36.25900583281677,"green.png"));
        baseStationList.add(new BaseStation(1414L,50.05176918503963,36.16693199198488,"yellow.png"));
        baseStationList.add(new BaseStation(1415L,50.04801093619062,36.226120970002455,"green.png"));
        baseStationList.add(new BaseStation(1416L,50.026305749054416,36.18624724872549,"green.png"));
        baseStationList.add(new BaseStation(1417L,50.0258007179323,36.20596645444286,"green.png"));
        baseStationList.add(new BaseStation(1418L,50.025652794490696,36.16717004368746,"grey.png"));
        baseStationList.add(new BaseStation(1419L,50.001666377231174,36.24035001595226,"grey.png"));
        baseStationList.add(new BaseStation(1420L,50.01437737000211,36.256723528432474,"grey.png"));
        baseStationList.add(new BaseStation(1421L,50.015990746572584,36.26959315587234,"yellow.png"));
        baseStationList.add(new BaseStation(1422L,50.023457458383916,36.25609868518529,"green.png"));
        baseStationList.add(new BaseStation(1423L,49.99911680503595,36.23385040694558,"grey.png"));
        baseStationList.add(new BaseStation(1424L,50.05396517258603,36.186017265453245,"yellow.png"));
        baseStationList.add(new BaseStation(1425L,50.046545467389876,36.21855468605136,"yellow.png"));
        baseStationList.add(new BaseStation(1426L,50.04234828798144,36.232201718371186,"yellow.png"));
        baseStationList.add(new BaseStation(1427L,50.00571375580739,36.16451289300733,"yellow.png"));
        baseStationList.add(new BaseStation(1428L,50.043350989485276,36.14551788306753,"green.png"));
        baseStationList.add(new BaseStation(1429L,50.0259328866249,36.242145571753184,"grey.png"));
        baseStationList.add(new BaseStation(1430L,50.05041117850797,36.17364038718165,"yellow.png"));
        baseStationList.add(new BaseStation(1431L,50.00935999353899,36.26852278856438,"yellow.png"));
        baseStationList.add(new BaseStation(1432L,50.04718889861647,36.166338994545185,"yellow.png"));
        baseStationList.add(new BaseStation(1433L,50.050324321027574,36.18513131639432,"green.png"));
        baseStationList.add(new BaseStation(1434L,50.056253155090104,36.15072930797487,"yellow.png"));
        baseStationList.add(new BaseStation(1435L,50.05570961705999,36.18225483224674,"yellow.png"));
        baseStationList.add(new BaseStation(1436L,50.047575876136676,36.219420320080445,"green.png"));
        baseStationList.add(new BaseStation(1437L,50.025945956590675,36.24097013231142,"grey.png"));
        baseStationList.add(new BaseStation(1438L,50.01230986118381,36.17382844148062,"grey.png"));
        baseStationList.add(new BaseStation(1439L,50.01156500542936,36.19231116661393,"yellow.png"));
        baseStationList.add(new BaseStation(1440L,50.03196332843052,36.17085057292297,"yellow.png"));
        baseStationList.add(new BaseStation(1441L,50.05201921764794,36.197388369675394,"yellow.png"));
        baseStationList.add(new BaseStation(1442L,50.01565643638142,36.232997820194896,"yellow.png"));
        baseStationList.add(new BaseStation(1443L,50.032752412354,36.21292731602031,"yellow.png"));
        baseStationList.add(new BaseStation(1444L,50.038046970969134,36.182266702621746,"grey.png"));
        baseStationList.add(new BaseStation(1445L,49.99978168016128,36.187157939337595,"green.png"));
        baseStationList.add(new BaseStation(1446L,50.04104756981474,36.20222818632479,"grey.png"));
        baseStationList.add(new BaseStation(1447L,50.01191386049351,36.273694668520534,"yellow.png"));
        baseStationList.add(new BaseStation(1448L,50.05500311675135,36.19273817369455,"yellow.png"));
        baseStationList.add(new BaseStation(1449L,50.022157454244024,36.143627647062004,"green.png"));
        baseStationList.add(new BaseStation(1450L,50.0564655272903,36.16001149206713,"yellow.png"));
        baseStationList.add(new BaseStation(1451L,50.0443032554048,36.224900024042995,"yellow.png"));
        baseStationList.add(new BaseStation(1452L,49.99885180645717,36.14694646364114,"grey.png"));
        baseStationList.add(new BaseStation(1453L,50.05285764165501,36.219087922586425,"grey.png"));
        baseStationList.add(new BaseStation(1454L,50.03028629583508,36.1959334143026,"grey.png"));
        baseStationList.add(new BaseStation(1455L,50.029807849247675,36.23114114447333,"grey.png"));
        baseStationList.add(new BaseStation(1456L,50.032688967523455,36.246515800590174,"yellow.png"));
        baseStationList.add(new BaseStation(1457L,50.04883320743315,36.25177957066475,"yellow.png"));
        baseStationList.add(new BaseStation(1458L,50.040779012289676,36.21733173253642,"grey.png"));
        baseStationList.add(new BaseStation(1459L,50.00588010119597,36.17372472989737,"yellow.png"));
        baseStationList.add(new BaseStation(1460L,50.01209672267444,36.23752931077527,"grey.png"));
        baseStationList.add(new BaseStation(1461L,50.00644045139856,36.14734190466589,"grey.png"));
        baseStationList.add(new BaseStation(1462L,50.04523070556493,36.18942521606604,"grey.png"));
        baseStationList.add(new BaseStation(1463L,50.055850148909485,36.1799493059859,"grey.png"));
        baseStationList.add(new BaseStation(1464L,50.02472078338738,36.25564632583209,"yellow.png"));
        baseStationList.add(new BaseStation(1465L,50.05230948875078,36.212496040017896,"green.png"));
        baseStationList.add(new BaseStation(1466L,50.00103629131581,36.23260588420291,"yellow.png"));
        baseStationList.add(new BaseStation(1467L,50.03580173736208,36.14320034339045,"yellow.png"));
        baseStationList.add(new BaseStation(1468L,50.04162496639805,36.16119307826514,"grey.png"));
        baseStationList.add(new BaseStation(1469L,50.02243405133444,36.18749502717762,"yellow.png"));
        baseStationList.add(new BaseStation(1470L,49.99982325739492,36.274971913010035,"grey.png"));
        baseStationList.add(new BaseStation(1471L,50.02781102874326,36.271953276667944,"green.png"));
        baseStationList.add(new BaseStation(1472L,50.047975114970626,36.211344588886334,"green.png"));
        baseStationList.add(new BaseStation(1473L,50.0482054117723,36.21425378855568,"grey.png"));
        baseStationList.add(new BaseStation(1474L,50.038583533470586,36.25089109710018,"grey.png"));
        baseStationList.add(new BaseStation(1475L,50.054924993897686,36.26400776007182,"green.png"));
        baseStationList.add(new BaseStation(1476L,50.03892793883779,36.17918922677498,"yellow.png"));
        baseStationList.add(new BaseStation(1477L,50.040227998131485,36.17152664106845,"yellow.png"));
        baseStationList.add(new BaseStation(1478L,50.028509646679765,36.23714362938085,"grey.png"));
        baseStationList.add(new BaseStation(1479L,50.02841047240884,36.210697506801324,"yellow.png"));
        baseStationList.add(new BaseStation(1480L,50.05257760974371,36.20934638380143,"green.png"));
        baseStationList.add(new BaseStation(1481L,50.022296740631475,36.21865528115629,"grey.png"));
        baseStationList.add(new BaseStation(1482L,50.01003911486856,36.160730344000285,"grey.png"));
        baseStationList.add(new BaseStation(1483L,50.04680961935807,36.17516047545513,"grey.png"));
        baseStationList.add(new BaseStation(1484L,50.00451874791179,36.146592499662376,"yellow.png"));
        baseStationList.add(new BaseStation(1485L,50.03408939221222,36.23331831753518,"green.png"));
        baseStationList.add(new BaseStation(1486L,49.999663578837044,36.220853781588346,"green.png"));
        baseStationList.add(new BaseStation(1487L,50.01951992066739,36.24213892063193,"yellow.png"));
        baseStationList.add(new BaseStation(1488L,50.03063186079183,36.25930954745553,"yellow.png"));
        baseStationList.add(new BaseStation(1489L,50.000404148150324,36.17538106896376,"green.png"));
        baseStationList.add(new BaseStation(1490L,50.003521880599095,36.22287369828092,"green.png"));
        baseStationList.add(new BaseStation(1491L,50.02365267167076,36.20285666671029,"grey.png"));
        baseStationList.add(new BaseStation(1492L,50.04474438672125,36.25764616300518,"grey.png"));
        baseStationList.add(new BaseStation(1493L,50.05556202501228,36.15675583906386,"yellow.png"));
        baseStationList.add(new BaseStation(1494L,50.044000953462785,36.174492679169475,"green.png"));
        baseStationList.add(new BaseStation(1495L,50.00966636005472,36.197626909515975,"grey.png"));
        baseStationList.add(new BaseStation(1496L,50.016402512564895,36.2533719234936,"yellow.png"));
        baseStationList.add(new BaseStation(1497L,50.00997059233595,36.17774642062378,"yellow.png"));
        baseStationList.add(new BaseStation(1498L,50.04495737725057,36.23833175743604,"green.png"));
        baseStationList.add(new BaseStation(1499L,50.04334577809527,36.269491583224976,"grey.png"));
        baseStationList.add(new BaseStation(1500L,50.01163739290706,36.25100586222581,"yellow.png"));
        baseStationList.add(new BaseStation(1501L,50.00862011068892,36.21148801032936,"yellow.png"));
        baseStationList.add(new BaseStation(1502L,50.02832481683862,36.22187621235591,"green.png"));
        baseStationList.add(new BaseStation(1503L,50.0469703256731,36.152229579506184,"green.png"));
        baseStationList.add(new BaseStation(1504L,50.02545182133315,36.24376294333095,"green.png"));
        baseStationList.add(new BaseStation(1505L,50.02129564411078,36.17811812864957,"yellow.png"));
        baseStationList.add(new BaseStation(1506L,50.02012259718674,36.172732416708754,"green.png"));
        baseStationList.add(new BaseStation(1507L,50.014157396507585,36.19170765521031,"grey.png"));
        baseStationList.add(new BaseStation(1508L,50.022899752900884,36.27173951971483,"grey.png"));
        baseStationList.add(new BaseStation(1509L,50.00741976313725,36.203050609805786,"grey.png"));
        baseStationList.add(new BaseStation(1510L,49.9995687089521,36.17418856510895,"green.png"));
        baseStationList.add(new BaseStation(1511L,50.03389832940795,36.2141242803487,"grey.png"));
        baseStationList.add(new BaseStation(1512L,50.04424706028692,36.214250696140994,"grey.png"));
        baseStationList.add(new BaseStation(1513L,50.0551065145398,36.22091174115346,"green.png"));
        baseStationList.add(new BaseStation(1514L,50.05258775516876,36.24763084582992,"green.png"));
        baseStationList.add(new BaseStation(1515L,50.0101302005277,36.238250639096265,"grey.png"));
        baseStationList.add(new BaseStation(1516L,50.02186702599283,36.24559399056949,"yellow.png"));
        baseStationList.add(new BaseStation(1517L,50.026868161948215,36.27450404508954,"yellow.png"));
        baseStationList.add(new BaseStation(1518L,50.04740744893061,36.214140775811444,"grey.png"));
        baseStationList.add(new BaseStation(1519L,50.01921398561899,36.25886389808895,"grey.png"));
        baseStationList.add(new BaseStation(1520L,50.02109117416052,36.166829884498924,"yellow.png"));
        baseStationList.add(new BaseStation(1521L,50.0262698680763,36.21324475717523,"green.png"));
        baseStationList.add(new BaseStation(1522L,50.01844316667763,36.20120299568589,"yellow.png"));
        baseStationList.add(new BaseStation(1523L,50.05214293205495,36.247188540545096,"grey.png"));
        baseStationList.add(new BaseStation(1524L,50.03292921163276,36.272665053960566,"yellow.png"));
        baseStationList.add(new BaseStation(1525L,50.04810104262697,36.207367180838474,"grey.png"));
        baseStationList.add(new BaseStation(1526L,50.017441906583684,36.25090792536369,"green.png"));
        baseStationList.add(new BaseStation(1527L,50.04034854603492,36.14459495550247,"green.png"));
        baseStationList.add(new BaseStation(1528L,50.028070934940445,36.25505772326238,"grey.png"));
        baseStationList.add(new BaseStation(1529L,50.03816439911566,36.23285397706283,"grey.png"));
        baseStationList.add(new BaseStation(1530L,50.00378954145475,36.14906567733635,"green.png"));
        baseStationList.add(new BaseStation(1531L,50.00987344900855,36.180198758258825,"green.png"));
        baseStationList.add(new BaseStation(1532L,50.02713984509259,36.2127211863764,"yellow.png"));
        baseStationList.add(new BaseStation(1533L,50.02189288202652,36.22225723100331,"yellow.png"));
        baseStationList.add(new BaseStation(1534L,50.04253390205511,36.27284379598865,"yellow.png"));
        baseStationList.add(new BaseStation(1535L,50.0225336832066,36.1545457827944,"green.png"));
        baseStationList.add(new BaseStation(1536L,50.03284760515936,36.20904998173074,"yellow.png"));
        baseStationList.add(new BaseStation(1537L,50.035859395249304,36.1438459597708,"grey.png"));
        baseStationList.add(new BaseStation(1538L,50.02206767524703,36.18259970145223,"grey.png"));
        baseStationList.add(new BaseStation(1539L,50.0109053938385,36.1993571145527,"green.png"));
        baseStationList.add(new BaseStation(1540L,50.02978901408582,36.163039025217046,"green.png"));
        baseStationList.add(new BaseStation(1541L,50.03371978639524,36.2645569178556,"grey.png"));
        baseStationList.add(new BaseStation(1542L,50.01386104060251,36.23228771387343,"grey.png"));
        baseStationList.add(new BaseStation(1543L,50.037184270170684,36.24453236786272,"yellow.png"));
        baseStationList.add(new BaseStation(1544L,49.9990656191286,36.21174655047801,"grey.png"));
        baseStationList.add(new BaseStation(1545L,50.03780234703828,36.17786827751249,"yellow.png"));
        baseStationList.add(new BaseStation(1546L,50.014236958738024,36.243903493835404,"grey.png"));
        baseStationList.add(new BaseStation(1547L,50.04871570810141,36.1742499913517,"grey.png"));
        baseStationList.add(new BaseStation(1548L,50.02626961232133,36.26871982237731,"grey.png"));
        baseStationList.add(new BaseStation(1549L,50.039875764430064,36.188826808915906,"grey.png"));
        baseStationList.add(new BaseStation(1550L,50.03607111167395,36.235964520654505,"grey.png"));
        baseStationList.add(new BaseStation(1551L,50.04579550201851,36.18778761162605,"grey.png"));
        baseStationList.add(new BaseStation(1552L,50.03517622347999,36.24567163869162,"yellow.png"));
        baseStationList.add(new BaseStation(1553L,50.01571085048664,36.15255648705394,"yellow.png"));
        baseStationList.add(new BaseStation(1554L,50.02602704427022,36.19471100426318,"green.png"));
        baseStationList.add(new BaseStation(1555L,50.04065037667925,36.17061878598906,"green.png"));
        baseStationList.add(new BaseStation(1556L,50.00689209974004,36.24388125349148,"yellow.png"));
        baseStationList.add(new BaseStation(1557L,50.046552613817056,36.14621336534526,"grey.png"));
        baseStationList.add(new BaseStation(1558L,50.04156250859009,36.22806854971276,"green.png"));
        baseStationList.add(new BaseStation(1559L,50.03426199014737,36.250386762235706,"green.png"));
        baseStationList.add(new BaseStation(1560L,50.05451525053465,36.250098449346694,"green.png"));
        baseStationList.add(new BaseStation(1561L,50.037355821476694,36.25891577324737,"green.png"));
        baseStationList.add(new BaseStation(1562L,50.02440853435448,36.210600674927406,"yellow.png"));
        baseStationList.add(new BaseStation(1563L,50.00199999594511,36.27381927840531,"green.png"));
        baseStationList.add(new BaseStation(1564L,50.05403231073126,36.25866183630421,"yellow.png"));
        baseStationList.add(new BaseStation(1565L,50.00416528119797,36.15453624169908,"yellow.png"));
        baseStationList.add(new BaseStation(1566L,50.00089411841748,36.267611702814435,"grey.png"));
        baseStationList.add(new BaseStation(1567L,50.01329853023287,36.25836782212613,"grey.png"));
        baseStationList.add(new BaseStation(1568L,50.04249827720241,36.16139544030514,"yellow.png"));
        baseStationList.add(new BaseStation(1569L,50.00166217795086,36.20724267552987,"green.png"));
        baseStationList.add(new BaseStation(1570L,50.046760746845706,36.21816786022342,"yellow.png"));
        baseStationList.add(new BaseStation(1571L,50.01273035826949,36.185021333509326,"yellow.png"));
        baseStationList.add(new BaseStation(1572L,50.0453474733705,36.266484915221525,"yellow.png"));
        baseStationList.add(new BaseStation(1573L,50.02933806255447,36.15786333088445,"grey.png"));
        baseStationList.add(new BaseStation(1574L,50.05290219740678,36.19088178817378,"green.png"));
        baseStationList.add(new BaseStation(1575L,50.04041496714922,36.22636253913549,"grey.png"));
        baseStationList.add(new BaseStation(1576L,50.05167335104596,36.24207165054244,"green.png"));
        baseStationList.add(new BaseStation(1577L,50.04068281214663,36.221318755303884,"green.png"));
        baseStationList.add(new BaseStation(1578L,50.04162730888177,36.179144954901204,"grey.png"));
        baseStationList.add(new BaseStation(1579L,50.05092593991108,36.267672512610346,"grey.png"));
        baseStationList.add(new BaseStation(1580L,50.01649963137626,36.26037656145121,"yellow.png"));
        baseStationList.add(new BaseStation(1581L,50.046638535000056,36.20074838399239,"yellow.png"));
        baseStationList.add(new BaseStation(1582L,50.00206689736717,36.23853053789813,"grey.png"));
        baseStationList.add(new BaseStation(1583L,50.00546408202942,36.25715541774531,"grey.png"));
        baseStationList.add(new BaseStation(1584L,50.047772251919156,36.177321304270464,"grey.png"));
        baseStationList.add(new BaseStation(1585L,50.00698480418998,36.27111478917618,"green.png"));
        baseStationList.add(new BaseStation(1586L,50.05547765407712,36.221430215898664,"grey.png"));
        baseStationList.add(new BaseStation(1587L,50.00779512449864,36.25510684437421,"green.png"));
        baseStationList.add(new BaseStation(1588L,50.0581728889795,36.17422104157467,"yellow.png"));
        baseStationList.add(new BaseStation(1589L,50.02618859159185,36.246692230777455,"yellow.png"));
        baseStationList.add(new BaseStation(1590L,50.052446326355636,36.2592898454798,"grey.png"));
        baseStationList.add(new BaseStation(1591L,50.013158589554706,36.24096185211761,"yellow.png"));
        baseStationList.add(new BaseStation(1592L,50.024266861695594,36.222367916379056,"grey.png"));
        baseStationList.add(new BaseStation(1593L,50.04156741442392,36.209766792153886,"grey.png"));
        baseStationList.add(new BaseStation(1594L,50.028662450435746,36.247257235222776,"yellow.png"));
        baseStationList.add(new BaseStation(1595L,50.041489228703,36.171482950664625,"grey.png"));
        baseStationList.add(new BaseStation(1596L,50.00502266485455,36.23728545194942,"green.png"));
        baseStationList.add(new BaseStation(1597L,50.0084823754153,36.26116737723757,"green.png"));
        baseStationList.add(new BaseStation(1598L,50.002136446317024,36.23047108996258,"yellow.png"));
        baseStationList.add(new BaseStation(1599L,50.01904025012362,36.192433108142524,"green.png"));
        baseStationList.add(new BaseStation(1600L,50.02675366125092,36.17073837067207,"yellow.png"));
        baseStationList.add(new BaseStation(1601L,50.00940142689291,36.21917629894347,"grey.png"));
        baseStationList.add(new BaseStation(1602L,50.004730469163945,36.182331759077044,"green.png"));
        baseStationList.add(new BaseStation(1603L,50.02513670964416,36.19545919790386,"green.png"));
        baseStationList.add(new BaseStation(1604L,50.000059073743955,36.15784996205272,"green.png"));
        baseStationList.add(new BaseStation(1605L,50.0022843786914,36.25970117912436,"yellow.png"));
        baseStationList.add(new BaseStation(1606L,50.05206844002245,36.143557593276995,"grey.png"));
        baseStationList.add(new BaseStation(1607L,50.02632675197381,36.26208232621596,"grey.png"));
        baseStationList.add(new BaseStation(1608L,50.05684071346619,36.18985703699278,"green.png"));
        baseStationList.add(new BaseStation(1609L,50.03100698324715,36.15905631064109,"green.png"));
        baseStationList.add(new BaseStation(1610L,50.048982534064834,36.20399259154135,"grey.png"));
        baseStationList.add(new BaseStation(1611L,49.99861360957675,36.18304737953258,"yellow.png"));
        baseStationList.add(new BaseStation(1612L,50.02421692171498,36.14352463839688,"green.png"));
        baseStationList.add(new BaseStation(1613L,50.050646289838284,36.154851890242696,"yellow.png"));
        baseStationList.add(new BaseStation(1614L,50.04963982495627,36.24173365227733,"yellow.png"));
        baseStationList.add(new BaseStation(1615L,50.023239324280155,36.20929717447538,"grey.png"));
        baseStationList.add(new BaseStation(1616L,50.04971760834354,36.19871479755679,"yellow.png"));
        baseStationList.add(new BaseStation(1617L,50.02849999912323,36.18793572993309,"green.png"));
        baseStationList.add(new BaseStation(1618L,50.052284880149045,36.163261147867075,"grey.png"));
        baseStationList.add(new BaseStation(1619L,50.00572710467131,36.177104613873325,"green.png"));
        baseStationList.add(new BaseStation(1620L,49.9991505301891,36.21218524039526,"yellow.png"));
        baseStationList.add(new BaseStation(1621L,50.04219259017489,36.156016600541165,"yellow.png"));
        baseStationList.add(new BaseStation(1622L,50.04214312054822,36.24383183198623,"yellow.png"));
        baseStationList.add(new BaseStation(1623L,50.029972154088824,36.22748009890395,"yellow.png"));
        baseStationList.add(new BaseStation(1624L,49.99857384490022,36.21598202543973,"grey.png"));
        baseStationList.add(new BaseStation(1625L,50.0001367522556,36.19385746618727,"grey.png"));
        baseStationList.add(new BaseStation(1626L,50.02505655432506,36.19391031875264,"green.png"));
        baseStationList.add(new BaseStation(1627L,50.055833047782926,36.19950781309004,"yellow.png"));
        baseStationList.add(new BaseStation(1628L,50.01781258964871,36.21962041661115,"grey.png"));
        baseStationList.add(new BaseStation(1629L,50.03957517830108,36.18661055417374,"green.png"));
        baseStationList.add(new BaseStation(1630L,50.01463464475975,36.26831565875676,"grey.png"));
        baseStationList.add(new BaseStation(1631L,50.056207412200735,36.20917826030407,"grey.png"));
        baseStationList.add(new BaseStation(1632L,50.01375195932155,36.207058894742005,"green.png"));
        baseStationList.add(new BaseStation(1633L,50.017174149831305,36.25368305774518,"green.png"));
        baseStationList.add(new BaseStation(1634L,50.013927372718115,36.190733151189676,"yellow.png"));
        baseStationList.add(new BaseStation(1635L,50.00775271910328,36.27283618938418,"grey.png"));
        baseStationList.add(new BaseStation(1636L,50.00325066000886,36.21451180499366,"green.png"));
        baseStationList.add(new BaseStation(1637L,50.05002861418549,36.20925627616597,"grey.png"));
        baseStationList.add(new BaseStation(1638L,50.026274812238626,36.16363113831892,"grey.png"));
        baseStationList.add(new BaseStation(1639L,50.042024251063594,36.147502514413304,"grey.png"));
        baseStationList.add(new BaseStation(1640L,50.01749240241358,36.25706214067989,"green.png"));
        baseStationList.add(new BaseStation(1641L,50.03248857593991,36.15020735243692,"yellow.png"));
        baseStationList.add(new BaseStation(1642L,50.05209744882933,36.15768711013865,"yellow.png"));
        baseStationList.add(new BaseStation(1643L,50.00939442816326,36.227107587797065,"green.png"));
        baseStationList.add(new BaseStation(1644L,50.00379665770373,36.18461304261899,"yellow.png"));
        baseStationList.add(new BaseStation(1645L,50.0411978429149,36.23599074496161,"grey.png"));
        baseStationList.add(new BaseStation(1646L,50.04399522718674,36.23667009259739,"yellow.png"));
        baseStationList.add(new BaseStation(1647L,50.000492307990555,36.27512864840182,"grey.png"));
        baseStationList.add(new BaseStation(1648L,50.03430866678255,36.26975033022552,"green.png"));
        baseStationList.add(new BaseStation(1649L,50.030853104595735,36.23286079269167,"grey.png"));
        baseStationList.add(new BaseStation(1650L,50.05249135595679,36.2562414576615,"green.png"));
        baseStationList.add(new BaseStation(1651L,50.02604035465791,36.207433415624166,"green.png"));
        baseStationList.add(new BaseStation(1652L,49.9980197211853,36.246372345622774,"yellow.png"));
        baseStationList.add(new BaseStation(1653L,50.02318845379193,36.203486175624334,"grey.png"));
        baseStationList.add(new BaseStation(1654L,50.00084782151413,36.17231661291078,"green.png"));
        baseStationList.add(new BaseStation(1655L,50.05540923937548,36.26350618374855,"yellow.png"));
        baseStationList.add(new BaseStation(1656L,50.05057954551003,36.16638497499716,"yellow.png"));
        baseStationList.add(new BaseStation(1657L,50.050614201907194,36.22779181527602,"grey.png"));
        baseStationList.add(new BaseStation(1658L,50.049663687798784,36.18103773695094,"yellow.png"));
        baseStationList.add(new BaseStation(1659L,50.04997392455621,36.24992414774408,"yellow.png"));
        baseStationList.add(new BaseStation(1660L,50.02454028078142,36.193305495208534,"grey.png"));
        baseStationList.add(new BaseStation(1661L,50.0419217791617,36.21810701537686,"green.png"));
        baseStationList.add(new BaseStation(1662L,50.024209434926604,36.22985054133602,"yellow.png"));
        baseStationList.add(new BaseStation(1663L,50.020498410836865,36.21122579853184,"yellow.png"));
        baseStationList.add(new BaseStation(1664L,50.01220761356822,36.206271288859085,"yellow.png"));
        baseStationList.add(new BaseStation(1665L,50.00542784064143,36.16494419068931,"yellow.png"));
        baseStationList.add(new BaseStation(1666L,50.03364203120569,36.25575110875505,"grey.png"));
        baseStationList.add(new BaseStation(1667L,50.049416174762115,36.160423063312265,"green.png"));
        baseStationList.add(new BaseStation(1668L,50.05726889378866,36.247593124114104,"yellow.png"));
        baseStationList.add(new BaseStation(1669L,50.05307825173173,36.256247420076505,"grey.png"));
        baseStationList.add(new BaseStation(1670L,50.01746438555597,36.147169334296535,"yellow.png"));
        baseStationList.add(new BaseStation(1671L,50.0469833048825,36.25300622832941,"yellow.png"));
        baseStationList.add(new BaseStation(1672L,50.02678307163646,36.168863269564696,"grey.png"));
        baseStationList.add(new BaseStation(1673L,50.00558821888433,36.15306547598027,"green.png"));
        baseStationList.add(new BaseStation(1674L,50.02581821236163,36.18725779766007,"yellow.png"));
        baseStationList.add(new BaseStation(1675L,50.01097488948338,36.15545794875487,"grey.png"));
        baseStationList.add(new BaseStation(1676L,50.05564253851125,36.1997930344952,"green.png"));
        baseStationList.add(new BaseStation(1677L,50.051439616395015,36.17054283273714,"green.png"));
        baseStationList.add(new BaseStation(1678L,50.040074869933704,36.15003027620611,"yellow.png"));
        baseStationList.add(new BaseStation(1679L,50.001586265663434,36.2675961221224,"grey.png"));
        baseStationList.add(new BaseStation(1680L,50.04918000274159,36.25495884048394,"green.png"));
        baseStationList.add(new BaseStation(1681L,50.00036004953887,36.206621266249954,"yellow.png"));
        baseStationList.add(new BaseStation(1682L,50.00884216457297,36.25132678842073,"grey.png"));
        baseStationList.add(new BaseStation(1683L,49.99843992588876,36.172105012602884,"yellow.png"));
        baseStationList.add(new BaseStation(1684L,50.04432945485536,36.25606684322875,"green.png"));
        baseStationList.add(new BaseStation(1685L,50.0068186706159,36.20744825978679,"yellow.png"));
        baseStationList.add(new BaseStation(1686L,49.99809039830973,36.150050518380624,"yellow.png"));
        baseStationList.add(new BaseStation(1687L,50.00638303093578,36.235756924561,"green.png"));
        baseStationList.add(new BaseStation(1688L,50.036171799532354,36.22940816989279,"yellow.png"));
        baseStationList.add(new BaseStation(1689L,50.04421157810731,36.260966719495315,"green.png"));
        baseStationList.add(new BaseStation(1690L,50.0081457698752,36.2393944081997,"yellow.png"));
        baseStationList.add(new BaseStation(1691L,50.0460278938242,36.21505013195275,"green.png"));
        baseStationList.add(new BaseStation(1692L,50.032326471216436,36.26292774263925,"grey.png"));
        baseStationList.add(new BaseStation(1693L,50.005106293430686,36.251996504889775,"grey.png"));
        baseStationList.add(new BaseStation(1694L,50.026400957845254,36.25823809333909,"green.png"));
        baseStationList.add(new BaseStation(1695L,50.03113802884425,36.22713231356096,"grey.png"));
        baseStationList.add(new BaseStation(1696L,50.01667915995537,36.251113906122384,"grey.png"));
        baseStationList.add(new BaseStation(1697L,50.02641415108508,36.164664531495845,"yellow.png"));
        baseStationList.add(new BaseStation(1698L,50.01127668739201,36.27281443597309,"yellow.png"));
        baseStationList.add(new BaseStation(1699L,50.046927398879234,36.2659748179897,"grey.png"));
        baseStationList.add(new BaseStation(1700L,50.00696082647329,36.25555918463323,"grey.png"));
        baseStationList.add(new BaseStation(1701L,50.0069675711311,36.274204946075145,"yellow.png"));
        baseStationList.add(new BaseStation(1702L,50.01495614083677,36.16647271245441,"green.png"));
        baseStationList.add(new BaseStation(1703L,50.03883968209513,36.24065209803075,"grey.png"));
        baseStationList.add(new BaseStation(1704L,50.056529419961095,36.24290707593579,"yellow.png"));
        baseStationList.add(new BaseStation(1705L,50.02258436568164,36.2101647258082,"grey.png"));
        baseStationList.add(new BaseStation(1706L,50.040252438469594,36.2203819113611,"yellow.png"));
        baseStationList.add(new BaseStation(1707L,50.044451304511966,36.25264204435197,"yellow.png"));
        baseStationList.add(new BaseStation(1708L,50.04649001960627,36.233195380129445,"yellow.png"));
        baseStationList.add(new BaseStation(1709L,50.04671180893351,36.17239033034508,"green.png"));
        baseStationList.add(new BaseStation(1710L,50.02713511927669,36.20239473613094,"yellow.png"));
        baseStationList.add(new BaseStation(1711L,50.04977991408427,36.19089373885197,"yellow.png"));
        baseStationList.add(new BaseStation(1712L,50.04167430069987,36.253473528806495,"grey.png"));
        baseStationList.add(new BaseStation(1713L,50.04261339350734,36.2602786296697,"grey.png"));
        baseStationList.add(new BaseStation(1714L,50.02405477663061,36.24288589160014,"grey.png"));
        baseStationList.add(new BaseStation(1715L,50.033208887546465,36.23696116238989,"grey.png"));
        baseStationList.add(new BaseStation(1716L,50.02744400962473,36.15240714137832,"yellow.png"));
        baseStationList.add(new BaseStation(1717L,50.001716235920185,36.24344878680415,"grey.png"));
        baseStationList.add(new BaseStation(1718L,50.0102131429376,36.16485553383336,"yellow.png"));
        baseStationList.add(new BaseStation(1719L,50.00691876711002,36.154995928061055,"grey.png"));
        baseStationList.add(new BaseStation(1720L,50.01174025942913,36.209607347227745,"green.png"));
        baseStationList.add(new BaseStation(1721L,50.04574318439822,36.271498827209854,"grey.png"));
        baseStationList.add(new BaseStation(1722L,50.05328588503744,36.223752971704016,"grey.png"));
        baseStationList.add(new BaseStation(1723L,50.00247449259592,36.197797668550464,"yellow.png"));
        baseStationList.add(new BaseStation(1724L,50.05759126756445,36.22438219910754,"grey.png"));
        baseStationList.add(new BaseStation(1725L,50.035428137432376,36.27164441971465,"green.png"));
        baseStationList.add(new BaseStation(1726L,50.01425701832818,36.172441123419304,"grey.png"));
        baseStationList.add(new BaseStation(1727L,50.01826442650287,36.17165862295326,"grey.png"));
        baseStationList.add(new BaseStation(1728L,50.002688580448506,36.26759542200017,"grey.png"));
        baseStationList.add(new BaseStation(1729L,50.0575168316571,36.221904319819146,"grey.png"));
        baseStationList.add(new BaseStation(1730L,50.02840885889668,36.24243158397414,"green.png"));
        baseStationList.add(new BaseStation(1731L,50.00522112236828,36.21954244827205,"green.png"));
        baseStationList.add(new BaseStation(1732L,50.0567626701671,36.16886130243114,"grey.png"));
        baseStationList.add(new BaseStation(1733L,50.05616406052019,36.23540586748851,"grey.png"));
        baseStationList.add(new BaseStation(1734L,49.999699011783775,36.20174272162174,"grey.png"));
        baseStationList.add(new BaseStation(1735L,50.00382108907186,36.21286205399289,"grey.png"));
        baseStationList.add(new BaseStation(1736L,50.045141150024676,36.25905445529605,"grey.png"));
        baseStationList.add(new BaseStation(1737L,50.04260434824849,36.1718977604738,"grey.png"));
        baseStationList.add(new BaseStation(1738L,50.04130964211569,36.22344864704172,"yellow.png"));
        baseStationList.add(new BaseStation(1739L,50.018409561953916,36.22310655397399,"yellow.png"));
        baseStationList.add(new BaseStation(1740L,50.04654611691976,36.242904613645806,"grey.png"));
        baseStationList.add(new BaseStation(1741L,50.03592564751884,36.23679574914044,"yellow.png"));
        baseStationList.add(new BaseStation(1742L,50.04645963066105,36.2617387613474,"yellow.png"));
        baseStationList.add(new BaseStation(1743L,50.02721195758343,36.16750509509123,"grey.png"));
        baseStationList.add(new BaseStation(1744L,50.01126352475538,36.21143993966851,"yellow.png"));
        baseStationList.add(new BaseStation(1745L,50.043342693576705,36.230793436463756,"green.png"));
        baseStationList.add(new BaseStation(1746L,50.05471468329404,36.16770744649568,"green.png"));
        baseStationList.add(new BaseStation(1747L,50.03139994421858,36.252511192910816,"green.png"));
        baseStationList.add(new BaseStation(1748L,50.035202876448096,36.242409662058805,"green.png"));
        baseStationList.add(new BaseStation(1749L,50.049850472992745,36.24316216663814,"yellow.png"));
        baseStationList.add(new BaseStation(1750L,50.00769684682999,36.19885057817538,"green.png"));
        baseStationList.add(new BaseStation(1751L,50.00545520215411,36.24763512308692,"green.png"));
        baseStationList.add(new BaseStation(1752L,49.999833363136986,36.1526173983095,"green.png"));
        baseStationList.add(new BaseStation(1753L,50.0256987440718,36.23362558980537,"yellow.png"));
        baseStationList.add(new BaseStation(1754L,50.01776546363922,36.155524262174566,"yellow.png"));
        baseStationList.add(new BaseStation(1755L,50.045118211256494,36.164685858392716,"green.png"));
        baseStationList.add(new BaseStation(1756L,50.04644628681578,36.17836582577482,"grey.png"));
        baseStationList.add(new BaseStation(1757L,50.00103812251487,36.27223934423921,"yellow.png"));
        baseStationList.add(new BaseStation(1758L,50.05226558951756,36.27483902882295,"green.png"));
        baseStationList.add(new BaseStation(1759L,50.03369202876772,36.15604736215258,"yellow.png"));
        baseStationList.add(new BaseStation(1760L,50.002320646068625,36.23606493003348,"yellow.png"));
        baseStationList.add(new BaseStation(1761L,50.05117742215444,36.21689479978167,"green.png"));
        baseStationList.add(new BaseStation(1762L,50.05342339101779,36.26170654286753,"grey.png"));
        baseStationList.add(new BaseStation(1763L,50.022462707199736,36.15426398059435,"green.png"));
        baseStationList.add(new BaseStation(1764L,50.01261725808578,36.25073867810991,"green.png"));
        baseStationList.add(new BaseStation(1765L,50.01842551037862,36.215935058338914,"green.png"));
        baseStationList.add(new BaseStation(1766L,50.021803582594856,36.250001049913465,"yellow.png"));
        baseStationList.add(new BaseStation(1767L,50.024126967091405,36.22981919027572,"green.png"));
        baseStationList.add(new BaseStation(1768L,50.0131653006373,36.1924894235643,"yellow.png"));
        baseStationList.add(new BaseStation(1769L,50.04185305502594,36.257373384473254,"yellow.png"));
        baseStationList.add(new BaseStation(1770L,50.03080952835731,36.2457602325014,"green.png"));
        baseStationList.add(new BaseStation(1771L,50.031577112714956,36.22235550780047,"green.png"));
        baseStationList.add(new BaseStation(1772L,50.04278126611595,36.19332480419615,"grey.png"));
        baseStationList.add(new BaseStation(1773L,50.01324820801068,36.24654066219475,"green.png"));
        baseStationList.add(new BaseStation(1774L,50.04780706149515,36.2568851916221,"yellow.png"));
        baseStationList.add(new BaseStation(1775L,50.011271258609696,36.14930049303092,"green.png"));
        baseStationList.add(new BaseStation(1776L,50.0158788213559,36.21556117562616,"green.png"));
        baseStationList.add(new BaseStation(1777L,50.024329410803006,36.16350722096533,"grey.png"));
        baseStationList.add(new BaseStation(1778L,50.022291253502345,36.172467143116876,"grey.png"));
        baseStationList.add(new BaseStation(1779L,50.02217914986517,36.25654312397975,"green.png"));
        baseStationList.add(new BaseStation(1780L,50.0571231110695,36.25110957358717,"grey.png"));
        baseStationList.add(new BaseStation(1781L,50.036104289753894,36.222421449540434,"yellow.png"));
        baseStationList.add(new BaseStation(1782L,50.03045038263335,36.217951015116114,"green.png"));
        baseStationList.add(new BaseStation(1783L,50.04755062256365,36.24860259873506,"yellow.png"));
        baseStationList.add(new BaseStation(1784L,50.03920940353359,36.169994413454674,"yellow.png"));
        baseStationList.add(new BaseStation(1785L,50.04345671336836,36.1563939160597,"grey.png"));
        baseStationList.add(new BaseStation(1786L,50.0470610540425,36.251608134400925,"green.png"));
        baseStationList.add(new BaseStation(1787L,50.012907903558585,36.17383865781417,"yellow.png"));
        baseStationList.add(new BaseStation(1788L,50.05304761281161,36.251475239289846,"grey.png"));
        baseStationList.add(new BaseStation(1789L,50.04960988610793,36.231901587393736,"yellow.png"));
        baseStationList.add(new BaseStation(1790L,50.043708114500134,36.188287383677334,"grey.png"));
        baseStationList.add(new BaseStation(1791L,50.04646085252048,36.21603160486882,"green.png"));
        baseStationList.add(new BaseStation(1792L,50.048835767466784,36.18425634098324,"grey.png"));
        baseStationList.add(new BaseStation(1793L,50.03037122122856,36.24939704156242,"green.png"));
        baseStationList.add(new BaseStation(1794L,50.05595048405357,36.218736211905224,"grey.png"));
        baseStationList.add(new BaseStation(1795L,50.0506972405511,36.24763330231025,"yellow.png"));
        baseStationList.add(new BaseStation(1796L,50.05820367234554,36.22264815815808,"yellow.png"));
        baseStationList.add(new BaseStation(1797L,50.00071132047424,36.26389501825699,"grey.png"));
        baseStationList.add(new BaseStation(1798L,50.03946592493884,36.21182051744256,"grey.png"));
        baseStationList.add(new BaseStation(1799L,50.02869458759355,36.260315236620954,"grey.png"));
        baseStationList.add(new BaseStation(1800L,50.05539645052286,36.21261744976562,"green.png"));
        baseStationList.add(new BaseStation(1801L,50.04589675698162,36.254564960234745,"grey.png"));
        baseStationList.add(new BaseStation(1802L,50.01175861334144,36.18326564123049,"yellow.png"));
        baseStationList.add(new BaseStation(1803L,50.02543769403056,36.23183794384039,"grey.png"));
        baseStationList.add(new BaseStation(1804L,50.046124734720706,36.220914352493566,"grey.png"));
        baseStationList.add(new BaseStation(1805L,50.00198031652528,36.157677617172,"grey.png"));
        baseStationList.add(new BaseStation(1806L,49.99815532023928,36.25221088975758,"grey.png"));
        baseStationList.add(new BaseStation(1807L,50.05043917786571,36.14533948468994,"yellow.png"));
        baseStationList.add(new BaseStation(1808L,50.00193967527332,36.2022926435003,"yellow.png"));
        baseStationList.add(new BaseStation(1809L,50.01620580357505,36.22321588618766,"green.png"));
        baseStationList.add(new BaseStation(1810L,49.998969306932054,36.18454250359713,"grey.png"));
        baseStationList.add(new BaseStation(1811L,50.04915123745707,36.20297782372711,"yellow.png"));
        baseStationList.add(new BaseStation(1812L,50.02863862730757,36.165438664989296,"green.png"));
        baseStationList.add(new BaseStation(1813L,50.00032039583641,36.27249031503794,"green.png"));
        baseStationList.add(new BaseStation(1814L,50.039597189943976,36.238900029678746,"yellow.png"));
        baseStationList.add(new BaseStation(1815L,50.02582289006625,36.22663432970839,"yellow.png"));
        baseStationList.add(new BaseStation(1816L,50.00572023571334,36.22919442285192,"green.png"));
        baseStationList.add(new BaseStation(1817L,50.045562487291875,36.2602409669972,"green.png"));
        baseStationList.add(new BaseStation(1818L,50.04591017523376,36.173243184361496,"green.png"));
        baseStationList.add(new BaseStation(1819L,50.040555684696386,36.17889917438515,"grey.png"));
        baseStationList.add(new BaseStation(1820L,50.04151004319402,36.157557694581186,"green.png"));
        baseStationList.add(new BaseStation(1821L,50.034929131346246,36.19570711832816,"green.png"));
        baseStationList.add(new BaseStation(1822L,50.00709006942921,36.18512243929763,"green.png"));
        baseStationList.add(new BaseStation(1823L,50.03882873560124,36.19022389468486,"grey.png"));
        baseStationList.add(new BaseStation(1824L,50.020505831861215,36.162120645961636,"green.png"));
        baseStationList.add(new BaseStation(1825L,50.01816699665727,36.235745532685314,"grey.png"));
        baseStationList.add(new BaseStation(1826L,50.0299671892696,36.18895678845442,"yellow.png"));
        baseStationList.add(new BaseStation(1827L,50.00603196694328,36.18835927045697,"green.png"));
        baseStationList.add(new BaseStation(1828L,50.014319565702436,36.24473334878516,"green.png"));
        baseStationList.add(new BaseStation(1829L,50.02219249678831,36.197368700862484,"green.png"));
        baseStationList.add(new BaseStation(1830L,50.032845105312646,36.15530800530584,"grey.png"));
        baseStationList.add(new BaseStation(1831L,50.00300793963075,36.23302513649472,"yellow.png"));
        baseStationList.add(new BaseStation(1832L,49.99808628668612,36.14756672535219,"green.png"));
        baseStationList.add(new BaseStation(1833L,50.033792566550204,36.22374319719128,"grey.png"));
        baseStationList.add(new BaseStation(1834L,50.031099696709504,36.26089440946572,"yellow.png"));
        baseStationList.add(new BaseStation(1835L,50.005398279298504,36.24193548750671,"yellow.png"));
        baseStationList.add(new BaseStation(1836L,50.0185831001626,36.273849299999625,"yellow.png"));
        baseStationList.add(new BaseStation(1837L,50.049580018614904,36.19829075379968,"grey.png"));
        baseStationList.add(new BaseStation(1838L,50.031275829027436,36.25021140215628,"green.png"));
        baseStationList.add(new BaseStation(1839L,50.014003578152995,36.23372908571329,"grey.png"));
        baseStationList.add(new BaseStation(1840L,50.03206280846831,36.21496292099687,"yellow.png"));
        baseStationList.add(new BaseStation(1841L,50.0221183416225,36.193648111879895,"yellow.png"));
        baseStationList.add(new BaseStation(1842L,50.0151539031296,36.21807890938908,"grey.png"));
        baseStationList.add(new BaseStation(1843L,49.99839004672947,36.182451688995314,"grey.png"));
        baseStationList.add(new BaseStation(1844L,50.00490992281229,36.16905586522833,"green.png"));
        baseStationList.add(new BaseStation(1845L,50.015321962571335,36.247972360986694,"yellow.png"));
        baseStationList.add(new BaseStation(1846L,49.99890116004289,36.274018255752026,"yellow.png"));
        baseStationList.add(new BaseStation(1847L,50.024875047744274,36.21120522833186,"grey.png"));
        baseStationList.add(new BaseStation(1848L,50.019684095513185,36.24171133646175,"grey.png"));
        baseStationList.add(new BaseStation(1849L,50.03430898678826,36.159190570383316,"grey.png"));
        baseStationList.add(new BaseStation(1850L,50.022801771075905,36.22563980633004,"yellow.png"));
        baseStationList.add(new BaseStation(1851L,50.03308081161009,36.27471912776318,"grey.png"));
        baseStationList.add(new BaseStation(1852L,50.00620116869857,36.264427848664695,"green.png"));
        baseStationList.add(new BaseStation(1853L,50.05769420001647,36.27281346512069,"green.png"));
        baseStationList.add(new BaseStation(1854L,50.04599231561389,36.1459820434591,"yellow.png"));
        baseStationList.add(new BaseStation(1855L,50.00998299328069,36.215117300037605,"green.png"));
        baseStationList.add(new BaseStation(1856L,50.01807253489025,36.26505048300666,"grey.png"));
        baseStationList.add(new BaseStation(1857L,50.01283697459954,36.19327102213671,"grey.png"));
        baseStationList.add(new BaseStation(1858L,50.0161908568557,36.26795361325051,"green.png"));
        baseStationList.add(new BaseStation(1859L,50.01688558402273,36.23392759498438,"green.png"));
        baseStationList.add(new BaseStation(1860L,50.01173766598916,36.22129466678493,"green.png"));
        baseStationList.add(new BaseStation(1861L,50.00378785057492,36.19799389866489,"yellow.png"));
        baseStationList.add(new BaseStation(1862L,50.000170829018685,36.22468994764117,"yellow.png"));
        baseStationList.add(new BaseStation(1863L,50.02354990906787,36.25481321023318,"green.png"));
        baseStationList.add(new BaseStation(1864L,50.02026105316139,36.21261390886278,"grey.png"));
        baseStationList.add(new BaseStation(1865L,50.046368027261025,36.23982682885517,"grey.png"));
        baseStationList.add(new BaseStation(1866L,50.04804866335553,36.16875107546687,"yellow.png"));
        baseStationList.add(new BaseStation(1867L,50.01485175586532,36.24398232296372,"green.png"));
        baseStationList.add(new BaseStation(1868L,50.01300528247208,36.22468267160738,"yellow.png"));
        baseStationList.add(new BaseStation(1869L,50.022784123892535,36.214406595168356,"green.png"));
        baseStationList.add(new BaseStation(1870L,50.0037383290821,36.18815527098167,"yellow.png"));
        baseStationList.add(new BaseStation(1871L,50.029206002280745,36.15772425311451,"yellow.png"));
        baseStationList.add(new BaseStation(1872L,50.03437532769644,36.209753181235754,"yellow.png"));
        baseStationList.add(new BaseStation(1873L,50.04204613932782,36.23046126392376,"grey.png"));
        baseStationList.add(new BaseStation(1874L,50.00220983195814,36.149821428531055,"yellow.png"));
        baseStationList.add(new BaseStation(1875L,50.00257355680259,36.26876938642383,"green.png"));
        baseStationList.add(new BaseStation(1876L,50.011061729641284,36.267570924154825,"green.png"));
        baseStationList.add(new BaseStation(1877L,50.02363123533314,36.15042522147721,"grey.png"));
        baseStationList.add(new BaseStation(1878L,50.03478442818289,36.2202762430133,"green.png"));
        baseStationList.add(new BaseStation(1879L,50.02261694922501,36.153003050030975,"yellow.png"));
        baseStationList.add(new BaseStation(1880L,50.019901039642036,36.249560609750716,"grey.png"));
        baseStationList.add(new BaseStation(1881L,50.03263913403782,36.179076773716815,"yellow.png"));
        baseStationList.add(new BaseStation(1882L,50.03348274593901,36.1791041167439,"green.png"));
        baseStationList.add(new BaseStation(1883L,50.05631504056976,36.252546974647686,"yellow.png"));
        baseStationList.add(new BaseStation(1884L,50.017691739324476,36.236284792563026,"grey.png"));
        baseStationList.add(new BaseStation(1885L,50.03607247297626,36.21366504575107,"yellow.png"));
        baseStationList.add(new BaseStation(1886L,50.042552717616985,36.21131749550733,"green.png"));
        baseStationList.add(new BaseStation(1887L,50.038518987315996,36.171585912180774,"grey.png"));
        baseStationList.add(new BaseStation(1888L,50.037238716833855,36.23926555176158,"green.png"));
        baseStationList.add(new BaseStation(1889L,50.03872658397489,36.21303511234825,"grey.png"));
        baseStationList.add(new BaseStation(1890L,50.03313606513265,36.17375702651876,"grey.png"));
        baseStationList.add(new BaseStation(1891L,50.05506689793717,36.19561803515464,"green.png"));
        baseStationList.add(new BaseStation(1892L,50.00972896369882,36.162044751007386,"yellow.png"));
        baseStationList.add(new BaseStation(1893L,50.03359789550353,36.26861872120634,"green.png"));
        baseStationList.add(new BaseStation(1894L,50.04754774642092,36.26010486988304,"grey.png"));
        baseStationList.add(new BaseStation(1895L,50.026269247279856,36.263421416193125,"yellow.png"));
        baseStationList.add(new BaseStation(1896L,50.03385336438765,36.271749836986885,"yellow.png"));
        baseStationList.add(new BaseStation(1897L,50.04550835250967,36.17271866102132,"yellow.png"));
        baseStationList.add(new BaseStation(1898L,50.020742135694604,36.24899971223343,"green.png"));
        baseStationList.add(new BaseStation(1899L,50.01318284022048,36.231131090982245,"yellow.png"));
        baseStationList.add(new BaseStation(1900L,50.01193263569078,36.256186317083205,"yellow.png"));
        baseStationList.add(new BaseStation(1901L,50.018484330155076,36.22779454759652,"yellow.png"));
        baseStationList.add(new BaseStation(1902L,49.99803070146325,36.19543255448815,"yellow.png"));
        baseStationList.add(new BaseStation(1903L,50.025556510182696,36.17118304260259,"yellow.png"));
        baseStationList.add(new BaseStation(1904L,50.00992661680519,36.14318471389626,"grey.png"));
        baseStationList.add(new BaseStation(1905L,50.006726886285776,36.2701792941516,"green.png"));
        baseStationList.add(new BaseStation(1906L,50.02148333822032,36.23019781296924,"yellow.png"));
        baseStationList.add(new BaseStation(1907L,50.00137703042236,36.19104008772709,"grey.png"));
        baseStationList.add(new BaseStation(1908L,50.02199111261325,36.203601696620694,"grey.png"));
        baseStationList.add(new BaseStation(1909L,50.02560959509319,36.20861199870356,"grey.png"));
        baseStationList.add(new BaseStation(1910L,50.04053756217371,36.158921186802615,"green.png"));
        baseStationList.add(new BaseStation(1911L,50.00531544649971,36.15134064135751,"yellow.png"));
        baseStationList.add(new BaseStation(1912L,50.04499551236583,36.243877104492384,"yellow.png"));
        baseStationList.add(new BaseStation(1913L,50.05683672081517,36.21608402281768,"grey.png"));
        baseStationList.add(new BaseStation(1914L,50.05578509296356,36.17415425178319,"yellow.png"));
        baseStationList.add(new BaseStation(1915L,50.04226596518882,36.263036198830406,"green.png"));
        baseStationList.add(new BaseStation(1916L,50.04331485114358,36.24716684835994,"yellow.png"));
        baseStationList.add(new BaseStation(1917L,50.0279540813892,36.23239044225568,"yellow.png"));
        baseStationList.add(new BaseStation(1918L,50.01107254941962,36.26160283432904,"yellow.png"));
        baseStationList.add(new BaseStation(1919L,50.01096211614467,36.147624326823035,"grey.png"));
        baseStationList.add(new BaseStation(1920L,50.05394419195028,36.20126236732665,"green.png"));
        baseStationList.add(new BaseStation(1921L,50.000998460919476,36.18748110996384,"yellow.png"));
        baseStationList.add(new BaseStation(1922L,50.007463496346645,36.15052769427194,"grey.png"));
        baseStationList.add(new BaseStation(1923L,50.007015326151674,36.14743334857096,"yellow.png"));
        baseStationList.add(new BaseStation(1924L,50.00303208046492,36.20045479101117,"green.png"));
        baseStationList.add(new BaseStation(1925L,50.03216095053631,36.220150595979845,"grey.png"));
        baseStationList.add(new BaseStation(1926L,49.99847590662449,36.26087947023976,"yellow.png"));
        baseStationList.add(new BaseStation(1927L,50.003880844201916,36.246816224795225,"green.png"));
        baseStationList.add(new BaseStation(1928L,50.027941889962335,36.22996065935837,"green.png"));
        baseStationList.add(new BaseStation(1929L,50.038012790515296,36.23827650003241,"yellow.png"));
        baseStationList.add(new BaseStation(1930L,49.999729448982066,36.27093711023113,"yellow.png"));
        baseStationList.add(new BaseStation(1931L,50.05044071664321,36.27466918452279,"green.png"));
        baseStationList.add(new BaseStation(1932L,50.039514597540034,36.25488978600433,"green.png"));
        baseStationList.add(new BaseStation(1933L,50.030930410347395,36.274126574323816,"grey.png"));
        baseStationList.add(new BaseStation(1934L,50.05085232070678,36.245738998948326,"grey.png"));
        baseStationList.add(new BaseStation(1935L,50.04783869820031,36.2693830358088,"green.png"));
        baseStationList.add(new BaseStation(1936L,50.00338047507174,36.27451000824172,"grey.png"));
        baseStationList.add(new BaseStation(1937L,50.01391380166868,36.15981216333941,"grey.png"));
        baseStationList.add(new BaseStation(1938L,50.02436854939583,36.26491327808651,"yellow.png"));
        baseStationList.add(new BaseStation(1939L,49.99880876126758,36.230191787496665,"green.png"));
        baseStationList.add(new BaseStation(1940L,50.04153649248036,36.2611056240454,"yellow.png"));
        baseStationList.add(new BaseStation(1941L,50.045038566030314,36.18915849986362,"grey.png"));
        baseStationList.add(new BaseStation(1942L,50.02373065942018,36.199799505308306,"green.png"));
        baseStationList.add(new BaseStation(1943L,50.043230608912495,36.215857037508556,"yellow.png"));
        baseStationList.add(new BaseStation(1944L,50.04391552749371,36.215067655553156,"yellow.png"));
        baseStationList.add(new BaseStation(1945L,50.00637715055842,36.19847846104747,"green.png"));
        baseStationList.add(new BaseStation(1946L,50.007240117206976,36.18059051980228,"green.png"));
        baseStationList.add(new BaseStation(1947L,50.04792783711872,36.255916245498184,"green.png"));
        baseStationList.add(new BaseStation(1948L,50.05589543979349,36.16973363635208,"grey.png"));
        baseStationList.add(new BaseStation(1949L,50.00589445713561,36.210398126996665,"green.png"));
        baseStationList.add(new BaseStation(1950L,50.05441517109659,36.157772020475875,"grey.png"));
        baseStationList.add(new BaseStation(1951L,50.01324672036344,36.25221359058267,"green.png"));
        baseStationList.add(new BaseStation(1952L,50.00886456777667,36.27337981854674,"green.png"));
        baseStationList.add(new BaseStation(1953L,50.0390692164693,36.15885963439689,"grey.png"));
        baseStationList.add(new BaseStation(1954L,50.035925296414575,36.176428939521,"grey.png"));
        baseStationList.add(new BaseStation(1955L,50.034155755356636,36.261364549407006,"green.png"));
        baseStationList.add(new BaseStation(1956L,50.04119332338945,36.202587238158344,"yellow.png"));
        baseStationList.add(new BaseStation(1957L,50.05612093188586,36.25420337846984,"grey.png"));
        baseStationList.add(new BaseStation(1958L,50.00335286533178,36.16038475927917,"grey.png"));
        baseStationList.add(new BaseStation(1959L,50.05801877737272,36.16333556873738,"green.png"));
        baseStationList.add(new BaseStation(1960L,50.017671417525804,36.15752996158923,"green.png"));
        baseStationList.add(new BaseStation(1961L,50.01087763031879,36.25517946011311,"yellow.png"));
        baseStationList.add(new BaseStation(1962L,50.016842487046546,36.25675602973925,"grey.png"));
        baseStationList.add(new BaseStation(1963L,50.04385991325668,36.20955396288707,"grey.png"));
        baseStationList.add(new BaseStation(1964L,50.032928405002195,36.143846723290295,"grey.png"));
        baseStationList.add(new BaseStation(1965L,50.05343099252934,36.25414558174003,"yellow.png"));
        baseStationList.add(new BaseStation(1966L,50.043075435500725,36.2031633790495,"green.png"));
        baseStationList.add(new BaseStation(1967L,50.03879636677061,36.269565445112136,"yellow.png"));
        baseStationList.add(new BaseStation(1968L,50.04845400023143,36.151103948639474,"grey.png"));
        baseStationList.add(new BaseStation(1969L,50.05130709394149,36.17413426057735,"grey.png"));
        baseStationList.add(new BaseStation(1970L,50.01949411864874,36.197841511835776,"yellow.png"));
        baseStationList.add(new BaseStation(1971L,50.02057492297368,36.20945211321143,"green.png"));
        baseStationList.add(new BaseStation(1972L,50.009122098871984,36.228200815175825,"green.png"));
        baseStationList.add(new BaseStation(1973L,50.02318927415007,36.26877381779806,"grey.png"));
        baseStationList.add(new BaseStation(1974L,50.01865483845742,36.20339765985321,"yellow.png"));
        baseStationList.add(new BaseStation(1975L,50.05495249627708,36.161947920257404,"yellow.png"));
        baseStationList.add(new BaseStation(1976L,50.03124497421908,36.23470486450458,"yellow.png"));
        baseStationList.add(new BaseStation(1977L,50.04491857620647,36.21999986206558,"green.png"));
        baseStationList.add(new BaseStation(1978L,50.05652723611769,36.20846791754661,"grey.png"));
        baseStationList.add(new BaseStation(1979L,50.01020736717351,36.261065630557084,"grey.png"));
        baseStationList.add(new BaseStation(1980L,50.037926582005184,36.269413919245466,"green.png"));
        baseStationList.add(new BaseStation(1981L,50.02124020034185,36.14516410035384,"yellow.png"));
        baseStationList.add(new BaseStation(1982L,50.01382548635536,36.27565259481915,"green.png"));
        baseStationList.add(new BaseStation(1983L,50.02572440934475,36.24654513508656,"green.png"));
        baseStationList.add(new BaseStation(1984L,50.02343756744793,36.26775709127563,"green.png"));
        baseStationList.add(new BaseStation(1985L,50.01931146972183,36.15615247127673,"yellow.png"));
        baseStationList.add(new BaseStation(1986L,50.042412381257094,36.20647055160185,"yellow.png"));
        baseStationList.add(new BaseStation(1987L,50.04419726572246,36.2352239430852,"green.png"));
        baseStationList.add(new BaseStation(1988L,50.030067924769014,36.23190041602521,"yellow.png"));
        baseStationList.add(new BaseStation(1989L,50.00171734890728,36.21001201077283,"grey.png"));
        baseStationList.add(new BaseStation(1990L,50.00408294552349,36.17795775861521,"grey.png"));
        baseStationList.add(new BaseStation(1991L,50.03435666983202,36.16207640808611,"grey.png"));
        baseStationList.add(new BaseStation(1992L,50.05099577261967,36.212966331881404,"yellow.png"));
        baseStationList.add(new BaseStation(1993L,50.01031034477066,36.205265148780825,"green.png"));
        baseStationList.add(new BaseStation(1994L,50.04132884647561,36.2275871091629,"yellow.png"));
        baseStationList.add(new BaseStation(1995L,50.03230489973424,36.19465237352182,"yellow.png"));
        baseStationList.add(new BaseStation(1996L,50.047283351055256,36.15556127838002,"green.png"));
        baseStationList.add(new BaseStation(1997L,50.04057139278052,36.264073975472,"yellow.png"));
        baseStationList.add(new BaseStation(1998L,50.02326007594942,36.19557355340643,"grey.png"));
        baseStationList.add(new BaseStation(1999L,50.02799637568185,36.27497547248589,"grey.png"));


        return baseStationList;
    }
}
