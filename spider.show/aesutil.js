/**
 * aes加解密工具类
 */
var crypto = require('crypto');

var aesutil = module.exports = {};

// 默认密钥
var defaultSecretKey = "12345678901234567890123456789012";

/**
 * aes加密
 * @param data 待加密内容
 * @param key 必须为32位私钥
 * @returns {string}
 */
aesutil.encryption = function (data, key, iv) {
		iv = iv || "";
		key = key || defaultSecretKey;
    var clearEncoding = 'utf8';
    var cipherEncoding = 'base64';
    var cipherChunks = [];
    var cipher = crypto.createCipheriv('aes-256-ecb', key, iv);
    cipher.setAutoPadding(true);
    cipherChunks.push(cipher.update(data, clearEncoding, cipherEncoding));
    cipherChunks.push(cipher.final(cipherEncoding));
    return cipherChunks.join('');
}

/**
 * aes解密
 * @param data 待解密内容
 * @param key 必须为32位私钥
 * @returns {string}
 */
aesutil.decryption = function (data, key, iv) {
    if (!data) {
        return "";
    }
		iv = iv || "";
		key = key || defaultSecretKey;
    var clearEncoding = 'utf8';
    var cipherEncoding = 'base64';
    var cipherChunks = [];
    var decipher = crypto.createDecipheriv('aes-256-ecb', key, iv);
    decipher.setAutoPadding(true);
    cipherChunks.push(decipher.update(data, cipherEncoding, clearEncoding));
    cipherChunks.push(decipher.final(clearEncoding));
    return cipherChunks.join('');
}