import axios from 'axios';

axios.interceptors.response.use(
    function (response) {
      // 요청 성공 시 그대로 응답
      return response;
    },
    async function (error) {
      const originalRequest = error.config;

      // 서버에서 401 Unauthorized 또는 403 Forbidden 발생 시
      if ((error.response.status === 401 || error.response.status === 403) && !originalRequest._retry) {
        originalRequest._retry = true;

        try {
          // localStorage에서 user ID 가져오기
          const id = localStorage.getItem("id");

          // 서버로 userId를 보내서 Redis에 있는 Refresh Token으로 새로운 Access Token 요청
          const tokenResponse = await axios.post('http://hoopi.p-e.kr/api/hoopi/refresh-token', { id });

          if (tokenResponse.status === 200) {
            // 새 Access Token을 쿠키에 저장한 후 원래 요청을 다시 시도
            return axios(originalRequest);
          } else {
            // 리프레시 토큰 요청 실패 시
            console.error('Failed to refresh token');
            window.location.reload('/'); // 로그인 페이지로 리디렉션
            return Promise.reject(error);
          }

        } catch (refreshError) {
          console.error('Refresh token request failed:', refreshError);
          window.location.reload('/'); // 로그인 페이지로 리디렉션
          return Promise.reject(refreshError);
        }
      }

      // 그 외 에러는 그대로 리턴
      return Promise.reject(error);
    }
);
