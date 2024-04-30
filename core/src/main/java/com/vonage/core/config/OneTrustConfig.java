package com.vonage.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


/**
 * SOCD
 */
@ObjectClassDefinition(name = "OneTrust Integration", description = "Where To Buy Configuration")

public @interface OneTrustConfig {
    /**
     * @return oneTrustCode
    * Attributions
    */
 @AttributeDefinition(name = "OneTrust", type = AttributeType.STRING, description = "")
    String[] oneTrustCode() default  {"us-en:5e19ecb4-e871-4e63-bef2-ff975f583cc0",
                                       "gb-en:15366dc9-fe57-489a-ae1e-fa3a3db12cdf",
                                       "au-en:45d16065-8167-48f2-9f03-153e26c327fb",
                                       "my-en:d46213ef-ca15-439b-af13-78f8716d993e",
                                       "ph-en:0ea6fc2d-6515-4410-8128-068679708856",
                                       "tw-en:25c602bd-0cf3-48a0-86e7-9e1f7e9d2131",
                                       "hk-en:660854c7-e543-4e6f-bd60-e7f30f0efff0",
                                       "id-en:1af3bc9e-844b-481a-a1b8-a4b7a6d91a5e",
                                       "kr-ko:2c7b5ad1-097f-4096-be40-5f26005de3d6",
                                       "sg-en:341087a7-0707-472b-bf20-8a055f05bc84",
                                       "cn-zh:a99a8314-89d3-4a1f-a02f-65681f6a6955",
                                       "jp-ja:687be884-7991-416a-85a3-5680d56c49ab",
                                       "de-de:29a2618c-2372-48de-b7cc-2bd71b3347a3",
                                       "ru-ru:2bc42318-bb01-494a-b52e-6a075f3bd2c9",
                                       "nz-en:a1580327-377c-4784-928a-b217ac9a984a",
                                       "ca-fr:a73dabb4-ef40-4f53-9f84-fc1ce3a3ea36",
                                       "ca-en:001db9f2-48aa-4ecc-a13e-f6bc80a829cf",
                                       "es-es:06967eb2-4d38-4cb3-a104-407c6da8945d",
                                       "ar-es:71b7ee90-3507-420e-a00a-f51ec13a61de",
                                       "cl-es:16e20ec3-88a0-4e08-8412-95fcf79ae899",
                                       "co-es:ebfd9734-5fe1-4b4b-a38c-61fec77d4c95",
                                       "br-pt:503e4c8e-5bf6-4aee-beac-abcc4f08c7ef",
                                       "fr-fr:a73dabb4-ef40-4f53-9f84-fc1ce3a3ea36",
                                       "mx-es:b4eecbf3-21e8-4725-95d4-62635cbaa0e8"};
}
