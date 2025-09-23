import React, { useEffect, useRef } from 'react';

const AuthButton = ({ isAuthenticated, onAuthSuccess, onAuthError, onLogout }) => {
  const buttonDivRef = useRef(null);

  useEffect(() => {
    if (!isAuthenticated && window.google && window.google.accounts && window.google.accounts.id && buttonDivRef.current) {
      try {
        window.google.accounts.id.initialize({
          client_id: '32358567694-dmas57prhp74tvubf72thlq9j30e68ns.apps.googleusercontent.com',
          callback: (response) => {
            if (response && response.credential) {
              onAuthSuccess(response.credential);
            } else {
              onAuthError('No credential returned');
            }
          }
        });
        window.google.accounts.id.renderButton(buttonDivRef.current, { theme: 'outline', size: 'large' });
      } catch (e) {
        onAuthError('Failed to initialize Google Sign-In');
      }
    }
  }, [isAuthenticated, onAuthSuccess, onAuthError]);

  if (isAuthenticated) {
    return (
      <div>
        <p>✅ Authenticated with Google</p>
        <button className="auth-button" onClick={onLogout}>
          Logout
        </button>
      </div>
    );
  }

  return (
    <div>
      <p>Please authenticate with Google to search for news</p>
      <div ref={buttonDivRef}></div>
    </div>
  );
};

export default AuthButton;
