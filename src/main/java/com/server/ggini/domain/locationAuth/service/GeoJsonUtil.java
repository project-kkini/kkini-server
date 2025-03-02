package com.server.ggini.domain.locationAuth.service;

import com.server.ggini.global.error.exception.BusinessException;
import com.server.ggini.global.error.exception.ErrorCode;
import org.json.JSONException;
import org.locationtech.jts.geom.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;

public class GeoJsonUtil {
    // GeoJSON 파일 경로를 클래스 상수로 지정
    private static final String GEO_JSON_FILE_PATH = "src/main/resources/geojson/gangnam_seocho.geojson";  // 파일 경로

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    /**
     * 주어진 위도와 경도가 강남구 또는 서초구에 포함되는지 확인하는 메서드
     * @param userLatitude 사용자 위도
     * @param userLongitude 사용자 경도
     * @return 강남구 또는 서초구에 위치하는지 여부
     */
    public static boolean checkLocation(double userLatitude, double userLongitude) {
        try {
            // GeoJSON 파일에서 데이터를 읽어오기
            String geoJsonData = readGeoJsonFile(GEO_JSON_FILE_PATH);

            // GeoJSON 데이터를 JSONObject로 파싱
            JSONObject geoJsonObject = new JSONObject(geoJsonData);
            JSONArray features = geoJsonObject.getJSONArray("features");

            // 사용자의 위치를 Point 객체로 만들기
            Point userPoint = geometryFactory.createPoint(new Coordinate(userLongitude, userLatitude));

            // 강남구와 서초구의 좌표를 단순히 비교
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject geometry = feature.getJSONObject("geometry");
                String type = geometry.getString("type");

                // Geometry가 Polygon 또는 MultiPolygon일 때만 처리
                if ("Polygon".equals(type) || "MultiPolygon".equals(type)) {
                    // GeoJSON 좌표 추출
                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                    // Polygon 처리
                    if ("Polygon".equals(type)) {
                        // 첫 번째 배열의 좌표로 Polygon 생성
                        JSONArray polygonCoordinates = coordinates.getJSONArray(0);
                        LinearRing linearRing = createLinearRing(polygonCoordinates);
                        Polygon polygon = new Polygon(linearRing, null, geometryFactory);

                        // 해당 Polygon이 사용자의 위치를 포함하는지 확인
                        if (polygon.contains(userPoint)) {
                            return true;  // 사용자가 위치한 구를 반환
                        }
                    }
                    // MultiPolygon 처리
                    else if ("MultiPolygon".equals(type)) {
                        // 여러 개의 Polygon을 처리
                        for (int j = 0; j < coordinates.length(); j++) {
                            JSONArray polygonCoordinates = coordinates.getJSONArray(j).getJSONArray(0);
                            LinearRing linearRing = createLinearRing(polygonCoordinates);
                            Polygon polygon = new Polygon(linearRing, null, geometryFactory);

                            // 해당 Polygon이 사용자의 위치를 포함하는지 확인
                            if (polygon.contains(userPoint)) {
                                return true;  // 사용자가 위치한 구를 반환
                            }
                        }
                    }
                }
            }
        }  catch (IOException e) {
            throw new BusinessException(ErrorCode.GEO_JSON_FILE_READ_ERROR);
        } catch (JSONException e) {
            throw new BusinessException(ErrorCode.GEO_JSON_PARSING_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN_ERROR);
        }
        return false;  // 위치한 구가 없는 경우
    }

    /**
     * GeoJSON 파일을 읽어서 문자열로 반환하는 메서드
     * @param geoJsonFilePath GeoJSON 파일 경로
     * @return GeoJSON 데이터 문자열
     */
    private static String readGeoJsonFile(String geoJsonFilePath) throws IOException {
        FileReader fileReader = new FileReader(geoJsonFilePath);
        StringBuilder geoJsonData = new StringBuilder();
        int c;
        while ((c = fileReader.read()) != -1) {
            geoJsonData.append((char) c);
        }
        fileReader.close();
        return geoJsonData.toString();
    }

    /**
     * GeoJSON 좌표 배열을 이용하여 LinearRing 객체를 생성하는 메서드
     * @param coordinates GeoJSON 좌표 배열
     * @return LinearRing 객체
     */
    private static LinearRing createLinearRing(JSONArray coordinates) {
        Coordinate[] coords = new Coordinate[coordinates.length()];
        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coord = coordinates.getJSONArray(i);
            coords[i] = new Coordinate(coord.getDouble(0), coord.getDouble(1));
        }
        return geometryFactory.createLinearRing(coords);
    }
}
