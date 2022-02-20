// package com.example.system.enums;
//
// import cn.hutool.crypto.asymmetric.KeyType;
// import lombok.Getter;
//
// /**
//  * 加密密钥类型
//  */
// @Getter
// public enum SecurityKeyType {
//     /**
//      * 公钥
//      */
//     PublicKey(1),
//     /**
//      * 私钥
//      */
//     PrivateKey(2),
//     /**
//      * 密钥
//      */
//     SecretKey(3);
//
//     /**
//      * 密钥类型
//      */
//     private final int type;
//
//     SecurityKeyType(int type) {
//         this.type = type;
//     }
//
//     /**
//      * 返回对应{@link KeyType}密钥类型
//      */
//     public KeyType toKeyType() {
//         switch (this) {
//             case PrivateKey:
//                 return KeyType.PrivateKey;
//             case PublicKey:
//                 return KeyType.PublicKey;
//             default:
//                 return KeyType.SecretKey;
//         }
//     }
// }
//
