set p=C:\Users\Administrator\Desktop\gzly\html\site\html\f\img
echo ������ָ��Ŀ¼������ȫ�����������������ļ���ֻʣ�¿հ��ļ�
pause
echo �˲��������Ŀ¼��%p%���������ļ�
pause
echo ���������ˣ�ûɾ������ร�ȷ�Ͻ�ɾ��
pause
for /f "delims=" %%i   in ('dir  /b/a-d/s  %p%\*.*')  do (
	echo %%i
	copy /y clear.txt "%%i"
)
pause