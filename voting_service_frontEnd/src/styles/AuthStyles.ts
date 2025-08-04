/* eslint-disable prettier/prettier */
import styled from 'styled-components'

export const AuthContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f4f4f4;
  `

export const ErrorMessage = styled.div`
  color: #ff4444;
  background-color: #ffebee;
  padding: 10px;
  border-radius: 4px;
  margin-bottom: 15px;
  text-align: center;
`

export const LoadingOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 1.5rem;
  z-index: 1000;
`


export const AuthForm = styled.form`
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;
`

export const AuthInput = styled.input`
  padding: 12px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
`

export const AuthButton = styled.button`
  background: #4f46e5;
  color: white;
  border: none;
  padding: 12px;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background: #4338ca;
  }
`

export const SwitchAuth = styled.p`
  margin-top: 10px;
  font-size: 14px;
  text-align: center;

  a {
    color: #4f46e5;
    cursor: pointer;
    text-decoration: none;
    font-weight: bold;
  }
`
