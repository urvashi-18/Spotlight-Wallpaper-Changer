Dim WinScriptHost
Set WinScriptHost = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")
If (fso.FileExists(path)) Then
Else
Dim objFS, objFile
Set objFS = CreateObject("Scripting.FileSystemObject")
Set objFile = objFS.CreateTextFile("x")
WinScriptHost.Run Chr(34) & "data.bat" & Chr(34), 0
Set WinScriptHost = Nothing
End If