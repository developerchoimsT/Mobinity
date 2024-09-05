import axios from 'axios';

axios.interceptors.response.use(
    function (response) {
      // 요청 성공 시 그대로 응답
      return response;
    },
    async function (error) {
      const originalRequest = error.config;

      // 서버에서 401 Unauthorized 또는 JwtException 발생 시
      if ((error.response.status === 401 || error.response.status === 403) && !originalRequest._retry) {
        originalRequest._retry = true;

        // localStorage에서 user ID 가져오기
        const id = localStorage.getItem("id");

        try {
          // 서버로 userId를 보내서 Redis에 있는 Refresh Token으로 새로운 Access Token 요청
          await axios.post('http://hoopi.p-e.kr/api/hoopi/refresh-token', { param: { id } });

          // 서버가 새 Access Token을 쿠키에 저장했으므로 원래 요청을 다시 시도
          return axios(originalRequest);
        } catch (error) {
          console.error('Refresh token request failed:', error);

          // 토큰 갱신에 실패하면, 새로고침 전에 무한 루프 방지 플래그를 설정합니다
          if (!originalRequest._failed) {
            originalRequest._failed = true;
            window.location.reload('/');
          }
        }
      }

      // 그 외 에러는 그대로 리턴
      return Promise.reject(error);
    }
);
