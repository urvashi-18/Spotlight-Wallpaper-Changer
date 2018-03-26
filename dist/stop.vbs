Dim WinScriptHost
Set WinScriptHost = CreateObject("WScript.Shell")
Dim objFS, objFile
Set objFS = CreateObject("Scripting.FileSystemObject")
objFS.Deletefile("x")
Set WinScriptHost = Nothing