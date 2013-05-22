uniform mat4 u_MVPMatrix;

varying vec2 v_TexCoordinate; // Passed to the fragment shader
varying vec4 v_Color;         // Passed to the fragment shader

attribute vec4 a_Position;
attribute vec2 a_TexCoordinate;
uniform vec4 a_Color;

void main() {
	v_Color = a_Color;
	v_TexCoordinate = a_TexCoordinate;
	gl_Position = u_MVPMatrix * a_Position;
}