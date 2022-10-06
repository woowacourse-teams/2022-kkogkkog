// import { css } from '@emotion/react';

// import Input from '@/@components/@shared/Input';
// import { useAuthenticateForm } from '@/@hooks/ui/user/useAuthenticateForm';

// import * as Styled from './style';

// const MockLoginForm = () => {
//   const {
//     state: { email, password },
//     changeHandler: { onChangeEmail, onChangePassword },
//     submitHandler: { login: onSubmitForm },
//   } = useAuthenticateForm();

//   return (
//     <Styled.LoginForm onSubmit={onSubmitForm}>
//       <Input.HiddenLabel
//         id='email'
//         type='email'
//         label='이메일'
//         placeholder='이메일'
//         value={email}
//         css={css`
//           border-radius: 4px 4px 0 0;
//         `}
//         onChange={onChangeEmail}
//       />
//       <Input.HiddenLabel
//         id='password'
//         type='password'
//         label='비밀번호'
//         placeholder='비밀번호'
//         value={password}
//         css={css`
//           border-radius: 0 0 4px 4px;
//           margin-bottom: 36px;
//         `}
//         onChange={onChangePassword}
//       />
//       <button type='submit'>로그인</button>
//     </Styled.LoginForm>
//   );
// };

// export default MockLoginForm;
