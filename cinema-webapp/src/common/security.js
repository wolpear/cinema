import Cookies from 'js-cookie';
// eslint-disable-next-line camelcase
import jwt_decode from 'jwt-decode';

class Cookie {
  static removeLoginCookie() {
    Cookies.remove('access_token');
  }

  static getLoginCookie() {
    return Cookies.get('access_token');
  }

  static checkIfUserLoggedIn() {
    return !!this.getLoginCookie();
  }

  static getLogin() {
    const cookieToDecode = this.getLoginCookie();
    if (!cookieToDecode) { return null; }
    const decodedCookie = jwt_decode(cookieToDecode);
    return decodedCookie.name;
  }
}

export default Cookie;
