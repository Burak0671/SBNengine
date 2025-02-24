#version 460 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;
uniform float useFakeLighting;

uniform float numberOfRows; // Texture Atlas i�in
uniform vec2 offset; 		// Texture Atlas i�in

const float density = 0.00016; //sis yo�unluk
const float gradient = 30.0;  //sis e�im de�i�tirirken terrain vertexshaderi�da de�i�tir

void main(void)
{

	vec4 worldPosition = transformationMatrix * vec4(position,1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	pass_textureCoords = (textureCoords / numberOfRows) + offset;
	
	vec3 actualNormal = normal;
	if(useFakeLighting > 0.5) {
		actualNormal = vec3(0.0,1.0,0.0);
	}
	surfaceNormal = (transformationMatrix * vec4(actualNormal,0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;
	toCameraVector = (transpose(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
	
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance * density),gradient));
	visibility = clamp(visibility,0.0,1.0);
}