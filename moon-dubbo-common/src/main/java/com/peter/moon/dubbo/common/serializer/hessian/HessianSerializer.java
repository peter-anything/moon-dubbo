package com.peter.moon.dubbo.common.serializer.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.dto.VertexDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HessianSerializer {

    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output ho = new Hessian2Output(os);
        try {
            ho.writeObject(obj);
            ho.flush();
            byte[] result = os.toByteArray();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ho.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Hessian2Input hi = new Hessian2Input(is);
        try {
            Object result = hi.readObject();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                hi.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        D2RMessage<VertexDTO> d2RMessage = new D2RMessage();
        List<VertexDTO> records = new ArrayList<>();
        VertexDTO vertexDTO = new VertexDTO();
        vertexDTO.setName("杨守杰");
        vertexDTO.setCode("dee8725aba3cba1ec443d8260497e22b");
        vertexDTO.setGraphlabel("人物");
        vertexDTO.getProperties().put("性别", "男");
        vertexDTO.getProperties().put("年龄", "54");
        records.add(vertexDTO);
        d2RMessage.setRecords(records);
        d2RMessage.setMessageId(vertexDTO.getCode());
//        d2RMessage.setTaskId(12333333333333L);
        byte[] bytes = new HessianSerializer().serialize(d2RMessage);
        byte[] newBytes = new byte[] {
                77, 116, 0, 42, 99, 111, 109, 46, 112, 101, 116, 101, 114, 46, 109, 111, 111, 110, 46, 100, 117, 98, 98, 111, 46, 99, 111, 109, 109, 111, 110, 46, 100, 116, 111, 46, 68, 50, 82, 77, 101, 115, 115, 97, 103, 101, 83, 0, 9, 109, 101, 115, 115, 97, 103, 101, 73, 100, 83, 0, 32, 100, 101, 101, 56, 55, 50, 53, 97, 98, 97, 51, 99, 98, 97, 49, 101, 99, 52, 52, 51, 100, 56, 50, 54, 48, 52, 57, 55, 101, 50, 50, 98, 83, 0, 6, 116, 97, 115, 107, 73, 100, 78, 83, 0, 7, 114, 101, 99, 111, 114, 100, 115, 86, 108, 0, 0, 0, 1, 77, 116, 0, 41, 99, 111, 109, 46, 112, 101, 116, 101, 114, 46, 109, 111, 111, 110, 46, 100, 117, 98, 98, 111, 46, 99, 111, 109, 109, 111, 110, 46, 100, 116, 111, 46, 86, 101, 114, 116, 101, 120, 68, 84, 79, 83, 0, 10, 103, 114, 97, 112, 104, 108, 97, 98, 101, 108, 83, 0, 2, -28, -70, -70, -25, -119, -87, 83, 0, 4, 99, 111, 100, 101, 83, 0, 32, 100, 101, 101, 56, 55, 50, 53, 97, 98, 97, 51, 99, 98, 97, 49, 101, 99, 52, 52, 51, 100, 56, 50, 54, 48, 52, 57, 55, 101, 50, 50, 98, 83, 0, 4, 110, 97, 109, 101, 83, 0, 3, -26, -99, -88, -27, -82, -120, -26, -99, -80, 83, 0, 10, 112, 114, 111, 112, 101, 114, 116, 105, 101, 115, 77, 116, 0, 0, 83, 0, 2, -27, -71, -76, -23, -66, -124, 83, 0, 2, 53, 52, 83, 0, 2, -26, -128, -89, -27, -120, -85, 83, 0, 1, -25, -108, -73, 122, 122, 122, 122

        };
        D2RMessage d2RMessage2 = (D2RMessage) new HessianSerializer().deserialize(bytes, D2RMessage.class);

        D2RMessage d2RMessage1 = (D2RMessage) new HessianSerializer().deserialize(newBytes, D2RMessage.class);
        System.out.println(d2RMessage1);
    }

}
