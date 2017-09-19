配置路径：Window-->Preferences-->Java-->Code Style-->Code Templates
																									-->Formatter
																									-->Organize Imports

在使用Import功能导入之前，需要对模板文件做如下修改：（或者导入完成后，在eclipse中修改）

Formatter
1. 对于v8之前的版本，需要各接口人定义适合自己的行长度。（目前是120）
line：77、208


Code Templates
1. 替换“@author XXX”中的“XXX”为自己的名字
2. 修改类注释中的“@version”和“@since”